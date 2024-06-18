from flask import Flask, request, jsonify
from sklearn.preprocessing import LabelEncoder
from sklearn.metrics.pairwise import cosine_similarity
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from dotenv import load_dotenv

import tensorflow as tf
import pandas as pd
import numpy as np
import jwt
import os

app = Flask(__name__)

# Memuat variabel lingkungan dari file .env
load_dotenv()

# Path ke file kredensial JSON (service account)
credential_path = os.path.join(os.getcwd(), 'serviceAccountKey.json')

# load model
model = tf.keras.models.load_model('cbf_model.h5', custom_objects={'mse': tf.keras.losses.MeanSquaredError()})

# Load csv
data = pd.read_csv(os.path.join(os.path.dirname(__file__), 'dataset', 'dataset-tourismBali.csv'))

# Memisahkan data
df = data[['Place_Id', 'Description', 'Category']] # DataFrame utama

# Menambahkan kolom yang diperlukan untuk output rekomendasi
# df2 = data[['Place_Id', 'Place_Name']]
df2 = data[['Place_Id', 'Place_Name', 'Description', 'Weekend Holiday Price', 'Weekday Price', 'Category', 'City', 'Rating', 'Alamat', 'Gambar']]

# DataFrame untuk prediksi
# df2 = df2.set_index('Place_Id').to_dict()['Place_Name']
df2 = df2.set_index('Place_Id').T.to_dict()

df_category = data[['Place_Id', 'Category', 'Rating']].set_index('Place_Id') # DataFrame untuk rekomendasi category

# Encode kategori
category_encoder = LabelEncoder()
df['Category_Encoded'] = category_encoder.fit_transform(df['Category'])

# Tokenisasi dan padding deskripsi
tokenizer = Tokenizer()
tokenizer.fit_on_texts(df['Description'])
sequences = tokenizer.texts_to_sequences(df['Description'])
padded_sequences = pad_sequences(sequences, padding='post')

# Data input untuk model
X_category = df['Category_Encoded'].values
X_description = padded_sequences

# Membuat matrix embeddings
embeddings = model.predict([X_category, X_description])

# Middleware untuk verifikasi token JWT (agar user hanya bisa mengakses fitur rekomendasi setelah login/autentikasi)
def verify_token(func):
    def wrapper(*args, **kwargs):
        auth_header = request.headers.get('Authorization', '')
        if not auth_header or not auth_header.startswith('Bearer '):
            return jsonify({'error': 'No token provided or malformed token'}), 401

        token = auth_header.split(' ')[1]
        try:
            payload = jwt.decode(token, os.getenv('JWT_SECRET'), algorithms=['HS256'])
            request.user = payload
            return func(*args, **kwargs)
        except jwt.InvalidTokenError:
            return jsonify({'error': 'Invalid token'}), 401
        
    wrapper.__name__ = f"verify_{func.__name__}"
    return wrapper

# Fungsi untuk memberikan rekomendasi
def recommend_by_id(place_id, embeddings, top_k=5):
    place_idx = df.index[df['Place_Id'] == place_id].tolist()[0]
    place_embedding = embeddings[place_idx]
    similarities = cosine_similarity([place_embedding], embeddings)[0]
    similar_indices = similarities.argsort()[::-1][1:top_k+1]
    similar_places = df.iloc[similar_indices]['Place_Id'].values
    return similar_places

def recommend_by_category(category_name, df=df_category, top_k=5):
    recommend = df[df['Category'] == category_name].sort_values(by='Rating', ascending=False).head(5).index.tolist()
    return recommend

# Endpoint untuk rekomendasi dengan input Place_Id
@app.route('/place-recommendation', methods=['GET'])
@verify_token
def give_recommendation_by_id():
    place_id = request.args.get('place_id')
    if place_id is None:
        return jsonify({'error': 'place_id parameter is required'}), 400
    try:
        place_id = int(place_id)
    except ValueError:
        return jsonify({'error': 'place_id must be an integer'}), 400
    
    recommendations = recommend_by_id(place_id, embeddings)
    place_record = []
    for i in recommendations:
        # place_record.append(df2[i])
        place_record.append({
            "Place_Name": df2[i]['Place_Name'],
            "Description": df2[i]['Description'],
            "Alamat": df2[i]['Alamat'],
            "Gambar": df2[i]['Gambar']
        })
    return jsonify({'recommendations' : place_record})

# Endpoint untuk rekomendasi dengan input Category
@app.route('/category-recommendation', methods=['GET'])
@verify_token
def give_recommendation_by_category():
    category = request.args.get('category')
    if category is None:
        return jsonify({'error': 'category parameter is required'}), 400
    try:
        category = str(category)
    except ValueError:
        return jsonify({'error': 'category must be an string'}), 400
    
    recommendations = recommend_by_category(category)
    place_record = []
    for i in recommendations:
        # place_record.append(df2[i])
        place_record.append({
            "Place_Name": df2[i]['Place_Name'],
            "Description": df2[i]['Description'],
            "Alamat": df2[i]['Alamat'],
            "Gambar": df2[i]['Gambar']
        })
    return jsonify({'recommendations' : place_record})

# Endpoint untuk detail rekomendasi dengan input Place_Id
@app.route('/place-detail', methods=['GET'])
@verify_token
def get_place_detail():
    place_id = request.args.get('place_id')
    if place_id is None:
        return jsonify({'error': 'place_id parameter is required'}), 400
    try:
        place_id = int(place_id)
    except ValueError:
        return jsonify({'error': 'place_id must be an integer'}), 400

    if place_id not in df2:
        return jsonify({'error': 'Place_Id not found'}), 404

    place_detail = {
        "Place_Name": df2[place_id]['Place_Name'],
        "Description": df2[place_id]['Description'],
        "Weekend_Holiday_Price": df2[place_id]['Weekend Holiday Price'],
        "Weekday_Price": df2[place_id]['Weekday Price'],
        "Category": df2[place_id]['Category'],
        "City": df2[place_id]['City'],
        "Rating": df2[place_id]['Rating'],
        "Alamat": df2[place_id]['Alamat'],
        "Gambar": df2[place_id]['Gambar']
    }
    return jsonify({'place_detail': place_detail})


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=int(os.environ.get('PORT', 8080)))
