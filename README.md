# Caption this

<img src="img/readme2.png" width=366px />

## Motivation

Image captioning on a mobile terminal using a machine learning model


<img src="img/readme3.png" width=201px />

## Objectives

-Development and implementation of a neuronal network that will take an image as input and will generate a sentence that summarise the contents of the image.
-Have a web server that runs the previous mentioned model
-An android application that will act as a client to our server




__Diagrama bloc a__  __aplicatiei__

<img src="img/readme6.jpg" width=500px />



## Neural network architecture

<img src="img/readme9.png" width=500px />



## The recurrent network(Decoder)

<img src="img/readme12.jpg" width=500px />

The decoder is connected to the penultimate layer of the VGG16 with a layer that reduces the size from 4096 to 512. 512 is also the number of internal states of our GRU architecture. The last layer will contain a layer of 10000 elements which is also the dimension of our vocabulary.


## Antrenarea retelei recurente

The recurent model was trained with the following hyperparameters

-Images were given under the format of the penultimate layer of vgg16.

-Optimizer: RMSprop 

20 epochs\,batch size 3000 images\.

One epoch took 5 hours on a nvidiaGTX 1060.

__Evolu?ia func?iei cost__  __pe__  __setul__  __de__  __validare__

<img src="img/readme15.png" width=500px />



Results

<img src="img/readme18.jpg" width=500px />

<img src="img/readme19.jpg" width=500px />

<img src="img/readme20.jpg" width=500px />

On the validation set


<img src="img/readme21.jpg" width=500px />



# Server implementation

Firebase authentification

<img src="img/readme24.png" width=500px />

Technologies used in server implementaion

Flask\(REST API\)

HTML/CSS\(UI\)

Docker\(Scalability\)

Firebase\(Authentification\,Scalability\)

Google Cloud\(Hosting\)

Scalability and loadbalancing

<img src="img/readme25.jpg" width=500px />

Docker instances


# Web application



<img src="img/readme28.png" width=500px />

Path to REST API for image description generation

POST http://127\.0\.0\.1:5000/api/predict and the image as payload



# Android Aplication

Structure

<img src="img/readme31.png" width=367px />

Introduction page

<img src="img/readme32.png" width=366px />

Login/Register Pages


<img src="img/readme33.png" width=372px />

<img src="img/readme34.png" width=365px />

Image selection page

<img src="img/readme35.png" width=363px />

Description generator

<img src="img/readme36.png" width=361px />








