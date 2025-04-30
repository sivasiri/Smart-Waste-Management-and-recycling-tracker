// src/pages/ManualEntryPage.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './ManualEntryPage.css';

const categories = [
  'plastic',
  'metal',
  'paper',
  'cardboard',
  'green-glass',
  'brown-glass',
  'white-glass',
  'other'
];

const ManualEntryPage = () => {
  const navigate = useNavigate();
  const [name, setName] = useState('');
  const [category, setCategory] = useState('');
  const [description, setDescription] = useState('');
  const [feedback, setFeedback] = useState('');

  const handleSubmit = async e => {
    e.preventDefault();
    setFeedback('');
    try {
      const token = localStorage.getItem('token');
      await axios.post(
        'http://localhost:8080/api/dashboard/barcode/manual',
        { name, category, description },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setFeedback(`
        🙏 Thanks for adding "<strong>${name}</strong>" as "<strong>${category}</strong>"!
        Together we’re making recycling smarter. 🌟
      `);
    } catch (err) {
      console.error(err);
      setFeedback('❌ Submission failed. Please try again.');
    }
  };

  return (
    <div className="manual-entry-container">
      <h2>➕ Manual Waste Entry</h2>

      {feedback ? (
        <>
          <section
            className="manual-feedback"
            dangerouslySetInnerHTML={{ __html: feedback.trim() }}
          />
          <button
            className="btn"
            onClick={() => navigate('/classification')}
          >
            ← Back to Scanner
          </button>
        </>
      ) : (
        <form className="manual-form" onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Item Name"
            value={name}
            onChange={e => setName(e.target.value)}
            required
            className="form-input"
          />

          <select
            value={category}
            onChange={e => setCategory(e.target.value)}
            required
            className="form-input"
          >
            <option value="">Select Category</option>
            {categories.map(c => (
              <option key={c} value={c}>{c}</option>
            ))}
          </select>

          <textarea
            placeholder="Description (optional)"
            value={description}
            onChange={e => setDescription(e.target.value)}
            className="form-input"
          />

          <div className="button-group">
            <button type="submit" className="btn">Submit</button>
            <button
              type="button"
              className="btn cancel-btn"
              onClick={() => navigate('/classification')}
            >
              Cancel
            </button>
          </div>
        </form>
      )}
    </div>
  );
};

export default ManualEntryPage;
