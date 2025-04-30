import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './ProfilePage.css';

const EditProfilePicturePage = () => {
  const navigate = useNavigate();
  const [preview, setPreview] = useState('');
  const [file, setFile] = useState(null);

  const handlePreview = e => {
    const f = e.target.files[0];
    if (f) {
      setFile(f);
      const reader = new FileReader();
      reader.onloadend = () => setPreview(reader.result);
      reader.readAsDataURL(f);
    }
  };

  const handleUpload = async () => {
    if (!file) return alert('Please select an image first');
    const formData = new FormData();
    formData.append('file', file);

    try {
      const token = localStorage.getItem('token');
      await axios.post(
        'http://localhost:8080/api/users/update-profile-picture',
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'multipart/form-data',
          },
        }
      );
      alert('✅ Profile picture updated!');
      navigate('/profile-picture');
    } catch (err) {
      console.error('❌ Upload failed:', err);
      alert('Upload failed');
    }
  };

  return (
    <div className="profile-container">
      <h2>📸 Update Profile Picture</h2>
      <div className="profile-picture-container">
        {preview && <img src={preview} alt="Preview" className="profile-pic" />}
      </div>
      <input
        type="file"
        accept="image/*"
        onChange={handlePreview}
        className="form-input"
      />
      <div className="button-group">
        <button className="btn" onClick={handleUpload}>
          Upload
        </button>
        <button
          className="btn cancel-btn"
          onClick={() => navigate('/profile-picture')}
        >
          Cancel
        </button>
      </div>
    </div>
  );
};

export default EditProfilePicturePage;
