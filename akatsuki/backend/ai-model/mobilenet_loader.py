from tensorflow.keras.applications import MobileNetV2
from tensorflow.keras.models import Model
from tensorflow.keras.layers import Dense, GlobalAveragePooling2D

def load_mobilenet_with_weights(weights_path):
    base_model = MobileNetV2(weights='imagenet', include_top=False, input_shape=(224, 224, 3))
    x = base_model.output
    x = GlobalAveragePooling2D()(x)
    x = Dense(12, activation='softmax')(x)  # 12-class problem

    model = Model(inputs=base_model.input, outputs=x)
    model.load_weights(weights_path)
    return model
