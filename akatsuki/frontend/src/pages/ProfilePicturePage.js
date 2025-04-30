import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './ProfilePage.css';

const ProfilePicturePage = () => {
  const navigate = useNavigate();
  const [picture, setPicture] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    (async () => {
      try {
        const token = localStorage.getItem('token');
        const res = await axios.get('http://localhost:8080/api/users/me', {
          headers: { Authorization: `Bearer ${token}` },
        });
        setPicture(res.data.profilePicture);
      } catch (err) {
        console.error('❌ Failed to fetch picture:', err);
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  if (loading) return <p>Loading...</p>;

  return (
    <div className="profile-container">
      <h2>📸 My Profile Picture</h2>
      {picture ? (
        <img
          src={`data:image/jpeg;base64,${picture}`}
          alt="Profile"
          className="profile-pic"
        />
      ) : (
        <p>No profile picture uploaded.</p>
      )}
      <div className="button-group">
        <button className="btn" onClick={() => navigate('/edit-profile-picture')}>
          Change Picture
        </button>
        <button className="btn cancel-btn" onClick={() => navigate('/profile')}>
          Back
        </button>
      </div>
    </div>
  );
};

export default ProfilePicturePage;
