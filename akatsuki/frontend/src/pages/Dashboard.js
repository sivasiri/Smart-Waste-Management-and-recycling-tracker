import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './Dashboard.css';
import { FaUserCircle } from 'react-icons/fa';

const Dashboard = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const checkToken = async () => {
      const token = localStorage.getItem('token');
      if (!token || token === 'undefined') {
        localStorage.removeItem('token');
        navigate('/login');
        return;
      }
      try {
        await axios.get('http://localhost:8080/api/users/dashboard', {
          headers: { Authorization: `Bearer ${token}` },
        });
      } catch (err) {
        localStorage.removeItem('token');
        navigate('/login');
      }
    };
    checkToken();
  }, [navigate]);

  return (
    <div className="dashboard-container">
      {/* Profile-icon only—header is now in Layout */}
      <div className="dashboard-profile-icon">
        <FaUserCircle
          className="profile-icon"
          onClick={() => navigate('/profile')}
          title="View Profile"
        />
      </div>

      {/* Main content */}
      <main className="dashboard-main">
        <h2>Welcome Back! 🌍</h2>
        <p>Explore the features below:</p>
        <ul className="features-list">
          <li onClick={() => navigate('/classification')} className="clickable">
            ♻️ AI Waste Categorization
          </li>
          <li onClick={() => navigate('/alerts')} className="clickable">
            🚛 Garbage Collection Alerts
          </li>
          <li onClick={() => navigate('/recycling-centers')} className="clickable">
            📍 Recycling Center Locator
          </li>
          <li onClick={() => navigate('/gamification')} className="clickable">
            🎯 Gamification & Contribution Tracking
          </li>
          <li onClick={() => navigate('/history')} className="clickable">
            📜 View Classification & Entry History
          </li>
        </ul>
      </main>

      {/* Footer */}
      <footer className="dashboard-footer">
        <p>&copy; 2025 Team Akatsuki. All rights reserved.</p>
        <p>About | Terms | Contact</p>
      </footer>
    </div>
  );
};

export default Dashboard;
