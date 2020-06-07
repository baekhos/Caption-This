from __future__ import division, print_function
# coding=utf-8
import sys
import os
import glob
import re
import numpy as np


import Caption as caption

# Flask utils
from flask import Flask, redirect, url_for, request, render_template, jsonify
from werkzeug.utils import secure_filename
from gevent.pywsgi import WSGIServer

UPLOAD_FOLDER = 'uploads/'

# Define a flask app
app = Flask(__name__)

app.secret_key = "secret key"

app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
# Model saved with Keras model.save()
MODEL_PATH = 'finalModel'

# Load your trained model
model=caption.Caption(MODEL_PATH)

ALLOWED_EXTENSIONS = set(['png', 'jpg', 'jpeg'])

print('Model loaded. Check http://127.0.0.1:5000/')


def model_predict(img_path, model):

    preds = model.generate_caption(img_path)
    return preds


@app.route('/', methods=['GET'])
def index():
    # Main page
    return render_template('index.html')



def allowed_file(filename):
	return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


@app.route('/predict', methods=['GET', 'POST'])
def upload():
    if request.method == 'POST':
        # Get the file from post request
        f = request.files['file']

        # Save the file to ./uploads
        basepath = os.path.dirname(__file__)
        file_path = os.path.join(
            basepath, app.config['UPLOAD_FOLDER'], secure_filename(f.filename))
        f.save(file_path)

        # Make prediction
        preds = model_predict(file_path, model)
        resp = jsonify({'message' : preds})
        resp.status_code = 400
        return preds
    return None

@app.route('/api/predict', methods=['POST'])
def upload_file():
    # check if the post request has the file part
    if 'file' not in request.files:
        print([x for x in request.files.items()])
        resp = jsonify({'message' : 'No picture in the request'})
        resp.status_code = 400
        return resp
    file = request.files['file']
    if file!=None and allowed_file(file.filename):
        filename = secure_filename(file.filename)
        basepath = os.path.dirname(__file__)
        file_path = os.path.join(basepath, app.config['UPLOAD_FOLDER'], secure_filename(filename))
        file.save(file_path)
        # Make prediction
        preds = model_predict(file_path, model)
        resp = jsonify({'message' : preds})
        resp.status_code = 201
        return resp
    else:
        resp = jsonify({'message' : 'Failed'})
        resp.status_code = 400
        return resp


if __name__ == '__main__':
    app.run()