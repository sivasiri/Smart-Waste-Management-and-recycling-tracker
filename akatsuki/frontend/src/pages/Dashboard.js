// File: src/pages/Dashboard.js

import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './Dashboard.css';
import { FaUserCircle } from 'react-icons/fa';

const Dashboard = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const checkToken = async () => {
      const token = localStorage.getItem("token");

      // Handle missing or undefined tokens
      if (!token || token === "undefined") {
        console.warn("No token found. Redirecting to login...");
        localStorage.removeItem("token");
        navigate("/login");
        return;
      }

      try {
        const res = await axios.get("http://localhost:8080/api/users/dashboard", {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        console.log("✅ Dashboard access granted:", res.data);
      } catch (err) {
        console.error("❌ Token validation failed or backend unreachable:", err);
        localStorage.removeItem("token");
        navigate("/login");
      }
    };

    checkToken();
  }, [navigate]);

  return (
    <div className="dashboard-container">
      {/* Header */}
      <header className="dashboard-header">
        <h1>Smart Waste Management and Recycling Tracker</h1>
        <FaUserCircle className="profile-icon" />
      </header>

      {/* Main content */}
      <main className="dashboard-main">
        <h2>Welcome Back! 🌍</h2>
        <p>Explore the features below:</p>
        <ul className="features-list">
          <li>♻️ AI Waste Categorization</li>
          <li>🚛 Garbage Collection Alerts</li>
          <li>📍 Recycling Center Locator</li>
          <li>🎯 Gamification & Contribution Tracking</li>
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
