import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './ProfilePage.css';

const ProfilePage = () => {
  const navigate = useNavigate();
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get('http://localhost:8080/api/users/me', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setProfile(res.data);
    } catch (err) {
      console.error('❌ Failed to fetch profile:', err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <p>Loading...</p>;

  return (
    <div className="profile-container">
      <h2>👤 My Profile</h2>
      <div className="profile-picture-container">
        {profile.profilePicture && (
          <img
            src={`data:image/jpeg;base64,${profile.profilePicture}`}
            alt="Profile"
            className="profile-pic"
          />
        )}
      </div>
      <p><strong>Name:</strong> {profile.firstName} {profile.lastName}</p>
      <p><strong>Email:</strong> {profile.email}</p>
      <p><strong>Phone:</strong> {profile.phone}</p>
      <p><strong>Address:</strong> {profile.address}</p>
      <p><strong>Age:</strong> {profile.age}</p>
      <p><strong>Sex:</strong> {profile.sex}</p>
      <div className="button-group">
        <button className="btn" onClick={() => navigate('/edit-profile')}>
          ✏️ Edit Profile
        </button>
        <button className="btn" onClick={() => navigate('/profile-picture')}>
          📸 View Picture
        </button>
      </div>
    </div>
  );
};

export default ProfilePage;
