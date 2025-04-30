// src/pages/EditProfilePage.js

import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './ProfilePage.css';

const EditProfilePage = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    firstName: '',
    lastName: '',
    phone: '',
    address: '',
    age: '',
    sex: ''
  });
  const [loading, setLoading] = useState(true);

  // Fetch current profile to prefill the form
  useEffect(() => {
    (async () => {
      try {
        const token = localStorage.getItem('token');
        const res = await axios.get('http://localhost:8080/api/users/me', {
          headers: { Authorization: `Bearer ${token}` }
        });
        const { firstName, lastName, phone, address, age, sex } = res.data;
        setForm({ firstName, lastName, phone, address, age, sex });
      } catch (err) {
        console.error('❌ Failed to load profile:', err);
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  const handleChange = (e) => {
    setForm(prev => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem('token');
      await axios.put(
        'http://localhost:8080/api/users/update',
        form,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert('✅ Profile updated successfully!');
      navigate('/profile');
    } catch (err) {
      console.error('❌ Update failed:', err);
      alert('Failed to update profile. Please try again.');
    }
  };

  if (loading) return <p>Loading form…</p>;

  return (
    <div className="profile-container">
      <h2>✏️ Edit Profile</h2>
      <form className="profile-form" onSubmit={handleSubmit}>
        {['firstName', 'lastName', 'phone', 'address', 'age', 'sex'].map(field => (
          <div className="form-group" key={field}>
            <label className="form-label">
              {field.replace(/([A-Z])/g, ' $1').replace(/^./, str => str.toUpperCase())}
            </label>
            <input
              type="text"
              name={field}
              value={form[field]}
              onChange={handleChange}
              className="form-input"
              required
            />
          </div>
        ))}
        <div className="button-group">
          <button type="submit" className="btn">Save</button>
          <button type="button" className="btn cancel-btn" onClick={() => navigate('/profile')}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
};

export default EditProfilePage;
