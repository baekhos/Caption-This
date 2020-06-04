from __future__ import division, print_function
# coding=utf-8
import sys
import os
import glob
import re
import numpy as np


import Caption as caption

# Flask utils
from flask import Flask, redirect, url_for, request, render_template
from werkzeug.utils import secure_filename
from gevent.pywsgi import WSGIServer

# Define a flask app
app = Flask(__name__)

# Model saved with Keras model.save()
MODEL_PATH = 'finalModel'

# Load your trained model
model=caption.Caption(MODEL_PATH)
# print('Model loaded. Start serving...')

# You can also use pretrained model from Keras
# Check https://keras.io/applications/
#from keras.applications.resnet50 import ResNet50

print('Model loaded. Check http://127.0.0.1:5000/')


def model_predict(img_path, model):

    preds = model.generate_caption(img_path)
    return preds


@app.route('/', methods=['GET'])
def index():
    # Main page
    return render_template('index.html')


@app.route('/predict', methods=['GET', 'POST'])
def upload():
    if request.method == 'POST':
        # Get the file from post request
        f = request.files['file']

        # Save the file to ./uploads
        basepath = os.path.dirname(__file__)
        file_path = os.path.join(
            basepath, 'uploads', secure_filename(f.filename))
        f.save(file_path)

        # Make prediction
        preds = model_predict(file_path, model)

        return preds
    return None


if __name__ == '__main__':
    app.run(debug=True)

