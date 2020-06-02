from PIL import Image
import numpy as np
import matplotlib.pyplot as plt
from tensorflow.keras.applications import VGG16
from tensorflow.keras.models import Model
from tensorflow.keras import backend as K
from tensorflow.keras.layers import Input, Dense, GRU, Embedding
from tensorflow.keras.optimizers import RMSprop
from tensorflow.keras.callbacks import ModelCheckpoint
import tensorflow as tf
from tensorflow.keras.preprocessing.text import Tokenizer
import coco

import time

class TokenizerWrap(Tokenizer):
    """Wrap the Tokenizer-class from Keras with more functionality."""
    
    def __init__(self, texts, num_words=None):
        """
        :param texts: List of strings with the data-set.
        :param num_words: Max number of words to use.
        """

        Tokenizer.__init__(self, num_words=num_words)

        # Create the vocabulary from the texts.
        self.fit_on_texts(texts)

        # Create inverse lookup from integer-tokens to words.
        self.index_to_word = dict(zip(self.word_index.values(),
                                      self.word_index.keys()))

    def token_to_word(self, token):
        """Lookup a single word from an integer-token."""

        word = " " if token == 0 else self.index_to_word[token]
        return word 

    def tokens_to_string(self, tokens):
        """Convert a list of integer-tokens to a string."""

        # Create a list of the individual words.
        words = [self.index_to_word[token]
                 for token in tokens
                 if token != 0]
        
        # Concatenate the words to a single string
        # with space between all the words.
        text = " ".join(words)

        return text
    
    def captions_to_tokens(self, captions_listlist):
        """
        Convert a list-of-list with text-captions to
        a list-of-list of integer-tokens.
        """
        
        # Note that text_to_sequences() takes a list of texts.
        tokens = [self.texts_to_sequences(captions_list)
                  for captions_list in captions_listlist]
        
        return tokens

class Caption():

    def __init__(self,model_path):

        self.img_size=(224, 224)
        num_words = 10000
        image_model = VGG16(include_top=True, weights='imagenet')
        transfer_layer = image_model.get_layer('fc2')
        self.image_model_transfer = Model(inputs=image_model.input, outputs=transfer_layer.output)
        self.decoder_model= tf.keras.models.load_model(model_path)
        self.mark_start = 'ssss '
        self.mark_end = ' eeee'
        self.token_start = 2
        self.token_end = 3
        _, filenames_train, captions_train = coco.load_records(train=True)
        captions_train_marked = self.mark_captions(captions_train)
        captions_train_flat = self.flatten(captions_train_marked)
        self.tokenizer = TokenizerWrap(texts=captions_train_flat, num_words=num_words)
        print(self.decoder_model.summary())
            
    def mark_captions(self,captions_listlist):
        captions_marked = [[self.mark_start + caption + self.mark_end   for caption in captions_list] for captions_list in captions_listlist]        
        return captions_marked


    def connect_decoder(self,transfer_values):
        # Map the transfer-values so the dimensionality matches
        # the internal state of the GRU layers. This means
        # we can use the mapped transfer-values as the initial state
        # of the GRU layers.
        initial_state = self.decoder_transfer_map(transfer_values)

        # Start the decoder-network with its input-layer.
        net = self.decoder_input
        
        # Connect the embedding-layer.
        net = self.decoder_embedding(net)
        
        # Connect all the GRU layers.
        net = self.decoder_gru1(net, initial_state=initial_state)
        net = self.decoder_gru2(net, initial_state=initial_state)
        net = self.decoder_gru3(net, initial_state=initial_state)

        # Connect the final dense layer that converts to
        # one-hot encoded arrays.
        decoder_output = self.decoder_dense(net)
        
        return decoder_output

    def flatten(self,captions_listlist):
        captions_list = [caption
                        for captions_list in captions_listlist
                        for caption in captions_list]
        
        return captions_list

    def load_image(self,path, size=None):
        """
        Load the image from the given file-path and resize it
        to the given size if not None.
        """
        
        # Load the image using PIL.
        img = Image.open(path)

        # Resize image if desired.
        if not size is None:
            img = img.resize(size=size, resample=Image.LANCZOS)

        # Convert image to numpy array.
        img = np.array(img)

        # Scale image-pixels so they fall between 0.0 and 1.0
        img = img / 255.0

        # Convert 2-dim gray-scale array to 3-dim RGB array.
        if (len(img.shape) == 2):
            img = np.repeat(img[:, :, np.newaxis], 3, axis=2)

        return img
    
    def generate_caption(self,image_path, max_tokens=30):
        """
        Generate a caption for the image in the given path.
        The caption is limited to the given number of tokens (words).
        """

        # Load and resize the image.
        image = self.load_image(image_path, size=self.img_size)
        
        # Expand the 3-dim numpy array to 4-dim
        # because the image-model expects a whole batch as input
        
        # so we give it a batch with just one image.
        image_batch = np.expand_dims(image, axis=0)

        # Process the image with the pre-trained image-model
        # to get the transfer-values.
        transfer_values = self.image_model_transfer.predict(image_batch)

        # Pre-allocate the 2-dim array used as input to the decoder.
        # This holds just a single sequence of integer-tokens,
        # but the decoder-model expects a batch of sequences.
        shape = (1, max_tokens)
        decoder_input_data = np.zeros(shape=shape, dtype=np.int)

        # The first input-token is the special start-token for 'ssss '.
        token_int = self.token_start

        # Initialize an empty output-text.
        output_text = ''

        # Initialize the number of tokens we have processed.
        count_tokens = 0

        # While we haven't sampled the special end-token for ' eeee'
        # and we haven't processed the max number of tokens.
        while token_int != self.token_end and count_tokens < max_tokens:
            # Update the input-sequence to the decoder
            # with the last token that was sampled.
            # In the first iteration this will set the
            # first element to the start-token.
            decoder_input_data[0, count_tokens] = token_int

            # Wrap the input-data in a dict for clarity and safety,
            # so we are sure we input the data in the right order.
            x_data = {
                'transfer_values_input': transfer_values,
                'decoder_input': decoder_input_data
            }

            # Note that we input the entire sequence of tokens
            # to the decoder. This wastes a lot of computation
            # because we are only interested in the last input
            # and output. We could modify the code to return
            # the GRU-states when calling predict() and then
            # feeding these GRU-states as well the next time
            # we call predict(), but it would make the code
            # much more complicated.
            
            # Input this data to the decoder and get the predicted output
            decoder_output = self.decoder_model.predict(x_data)

            # Get the last predicted token as a one-hot encoded array.
            # Note that this is not limited by softmax, but we just
            # need the index of the largest element so it doesn't matter.
            token_onehot = decoder_output[0, count_tokens, :]

            # Convert to an integer-token.
            token_int = np.argmax(token_onehot)

            # Lookup the word corresponding to this integer-token.
            sampled_word = self.tokenizer.token_to_word(token_int)

            # Append the word to the output-text.
            output_text += " " + sampled_word

            # Increment the token-counter.
            count_tokens += 1

        # This is the sequence of tokens output by the decoder.
        output_tokens = decoder_input_data[0]

        # Print the predicted caption.
        print("Predicted caption:")
        output_text=output_text[1].upper()+output_text[2:-4]
        print(output_text)
        print()
        return(output_text)

#Test
caption=Caption(model_path='finalModel')
now=time.time()
caption.generate_caption(image_path='test\\beach.jpg')
then=time.time()
print('Time lapsed: {}'.format(then-now))
now=time.time()
caption.generate_caption(image_path='test\\bear.jpg')
then=time.time()
print('Time lapsed: {}'.format(then-now))