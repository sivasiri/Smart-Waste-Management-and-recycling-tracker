// src/pages/ClassificationPage.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './ClassificationPage.css';
import ZXingBarcodeScanner from '../components/ZXingBarcodeScanner';

const recyclableCategories = [
  'plastic','metal','paper','cardboard',
  'green-glass','brown-glass','white-glass'
];

export default function ClassificationPage() {
  const navigate = useNavigate();

  // scanner active?
  const [scanningActive, setScanningActive] = useState(true);
  // creative message
  const [message, setMessage] = useState('');
  // per-feature loading flags
  const [loadingScan, setLoadingScan] = useState(false);
  const [loadingImage, setLoadingImage] = useState(false);

  // IMAGE PREVIEW
  const [imageFile, setImageFile] = useState(null);
  const [imagePreview, setImagePreview] = useState('');

  // 1️⃣ Barcode callback
  const handleBarcodeDetected = async code => {
    setLoadingScan(true);
    setScanningActive(false);
    setMessage('');

    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(
        `http://localhost:8080/api/dashboard/barcode/${encodeURIComponent(code)}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      const label = (
        res.data.getPredictedLabel ||
        res.data.getClassification ||
        ''
      ).toLowerCase();

      setMessage(
        recyclableCategories.includes(label)
          ? `🎉 Awesome! "${label}" is recyclable — thanks for saving our planet! 🌍`
          : `⚠️ "${label}" isn’t in our recyclable list. Dispose responsibly.`
      );
    } catch (err) {
      console.error(err);
      if (err.response?.status === 404) {
        setMessage(
          `🔍 Not in database. <button class="link-btn" onClick={() => navigate('/manual-entry')}>Add manually</button> or `
        );
      } else {
        setMessage('❌ Oops, something went wrong. Try again.');
      }
    } finally {
      setLoadingScan(false);
    }
  };

  // 2️⃣ Image change
  const handleImageChange = e => {
    const file = e.target.files[0];
    if (!file) return;
    setImageFile(file);
    const reader = new FileReader();
    reader.onloadend = () => setImagePreview(reader.result);
    reader.readAsDataURL(file);
    setMessage('');
  };

  // 3️⃣ Image submit
  const handleImageSubmit = async e => {
    e.preventDefault();
    if (!imageFile) {
      setMessage('🔔 Please select an image.');
      return;
    }

    setLoadingImage(true);
    setMessage('');

    try {
      const token = localStorage.getItem('token');
      const fd = new FormData();
      fd.append('file', imageFile);
      const res = await axios.post(
        'http://localhost:8080/api/dashboard/barcode/image/aiclassify',
        fd,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'multipart/form-data'
          }
        }
      );
      const label = (res.data.getClassification || '').toLowerCase();
      setMessage(
        recyclableCategories.includes(label)
          ? `📸 Cool! AI says "${label}" is recyclable—thank you! 🌱`
          : `🤖 AI says "${label}" isn’t recyclable. Please dispose properly.`
      );
    } catch (err) {
      console.error(err);
      setMessage('❌ Image classification failed. Try again.');
    } finally {
      setLoadingImage(false);
    }
  };

  return (
    <div className="classification-container">
      <h2>♻️ Waste Classification</h2>

      {/* Live Barcode Scan */}
      <section className="classification-section">
        <h3>📦 Live Barcode Scan</h3>

        {scanningActive && (
          <ZXingBarcodeScanner onDetected={handleBarcodeDetected} />
        )}
        {loadingScan && <p>Scanning…</p>}

        {!scanningActive && !loadingScan && (
          <button
            className="btn"
            onClick={() => {
              setMessage('');
              setScanningActive(true);
            }}
          >
            🔄 Scan another item
          </button>
        )}
      </section>

      {/* Creative Message */}
      {message && (
        <section
          className="classification-result"
          dangerouslySetInnerHTML={{ __html: message }}
        />
      )}

      {/* Image Upload */}
      <section className="classification-section">
        <h3>📷 Image Upload</h3>
        <form onSubmit={handleImageSubmit}>
          <input
            type="file"
            accept="image/*"
            className="form-input"
            onChange={handleImageChange}
            disabled={!!message}
          />
          {imagePreview && (
            <img src={imagePreview} alt="Preview" className="preview-pic" />
          )}
          <button
            type="submit"
            className="btn"
            disabled={loadingImage || !!message}
          >
            {loadingImage ? 'Classifying…' : 'Classify Image'}
          </button>
        </form>
      </section>

      {/* Manual Entry Link */}
      {!message && (
        <button className="btn" onClick={() => navigate('/manual-entry')}>
          ➕ Manual Entry
        </button>
      )}

      {/* Back */}
      <button
        className="btn cancel-btn"
        onClick={() => navigate('/dashboard')}
      >
        ← Back to Dashboard
      </button>
    </div>
  );
}
