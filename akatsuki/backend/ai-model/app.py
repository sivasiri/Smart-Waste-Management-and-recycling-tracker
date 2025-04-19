from flask import Flask, request, jsonify
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing import image
import numpy as np
from PIL import Image
import io

app = Flask(__name__)

# ✅ Load the downloaded model
model = load_model('Model.h5')  # Make sure Model.h5 is the correct file name

# ✅ Waste classification categories (in order!)
classes = ['cardboard', 'glass', 'metal', 'paper', 'plastic', 'trash']

@app.route('/classify', methods=['POST'])
def classify():
    try:
        file = request.files['file']
        img = Image.open(io.BytesIO(file.read())).convert('RGB')
        img = img.resize((150, 150))  # Input shape used in training
        img_array = image.img_to_array(img) / 255.0
        img_array = np.expand_dims(img_array, axis=0)

        prediction = model.predict(img_array)
        class_idx = np.argmax(prediction[0])
        confidence = float(np.max(prediction[0]))
        label = classes[class_idx]

        return jsonify({
            'classification': label,
            'confidence': round(confidence, 3)
        })

    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(port=5000, debug=True)
