// File: src/pages/LoginForm.js

import React, { useState } from 'react';
import './LoginForm.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { FaUserCircle } from 'react-icons/fa';

const LoginForm = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/api/users/login", {
        email,
        password
      });

      // ✅ Handle JSON token response properly
      const token = response.data.token;
      if (token) {
        localStorage.setItem("token", token);
        alert("Login successful!");
        navigate("/dashboard");
      } else {
        alert("Login failed. Token not received.");
      }
    } catch (err) {
      console.error("Login error:", err);
      alert("Login failed: " + (err.response?.data || "Backend not reachable"));
    }
  };

  return (
    <div className="login-container">
      <h2><FaUserCircle /> Login to Smart Waste App</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="email"
          placeholder="Enter your email"
          value={email}
          required
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          type="password"
          placeholder="Enter your password"
          value={password}
          required
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default LoginForm;
