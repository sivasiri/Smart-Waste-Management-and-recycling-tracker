// File: src/pages/WelcomePage.js

import React from 'react';
import { useNavigate } from 'react-router-dom';
import './WelcomePage.css';

const WelcomePage = () => {
  const navigate = useNavigate();

  const goToRegister = () => navigate('/register');
  const goToLogin = () => navigate('/login');

  return (
    <div className="welcome-container">
      <div className="welcome-content">
        <h1>♻️ Smart Waste Management</h1>
        <p>“Join the mission to keep Earth clean and green!”</p>

        <div className="button-group">
          <button onClick={goToRegister}>Register</button>
          <button onClick={goToLogin}>Login</button>
        </div>
      </div>

      <div className="welcome-image">
        <img src="/images/earth_recycle.jpg" alt="Save Earth" />
      </div>
    </div>
  );
};

export default WelcomePage;
