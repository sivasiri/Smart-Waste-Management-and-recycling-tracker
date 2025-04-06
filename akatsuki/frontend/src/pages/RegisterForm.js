// File: src/pages/RegisterForm.js

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './RegisterForm.css';
import axios from 'axios';

const RegisterForm = () => {
  const navigate = useNavigate();

  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    phone: '',
    address: '',
    age: '',
    sex: '',
    profilePicture: null
  });

  const stepMessages = [
    "You’re now an Eco Explorer!",
    "Great! You’re gaining green points.",
    "Now we know where to help you recycle!",
    "Almost there!",
    "You’ve unlocked the Eco-Warrior Badge!"
  ];

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    if (name === 'profilePicture') {
      setFormData({ ...formData, [name]: files[0] });
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleNext = () => {
    if (step < 5) setStep(step + 1);
  };

  const handleBack = () => {
    if (step > 1) setStep(step - 1);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const data = new FormData();
    Object.keys(formData).forEach(key => {
      if (formData[key]) data.append(key, formData[key]);
    });

    try {
      const response = await axios.post("http://localhost:8080/api/users/register", data);

      if (response.status === 200 && response.data.includes("successful")) {
        alert("Registration Successful!");
        navigate('/'); // redirect to welcome/login
      } else {
        alert("Registration failed: Unexpected server response");
      }
    } catch (err) {
      console.error("Registration Error:", err);
      alert("Registration failed: " + (err.response?.data || "Backend not reachable"));
    }
  };

  return (
    <div className="register-container">
      <div className="register-form">
        <h2>Welcome to Save Earth Initiative 🌍</h2>
        <p>{stepMessages[step - 1]}</p>

        <form onSubmit={handleSubmit}>
          {step === 1 && (
            <>
              <input type="text" name="firstName" placeholder="First Name" onChange={handleChange} required />
              <input type="text" name="lastName" placeholder="Last Name" onChange={handleChange} required />
            </>
          )}
          {step === 2 && (
            <>
              <input type="email" name="email" placeholder="Email" onChange={handleChange} required />
              <input type="password" name="password" placeholder="Password" onChange={handleChange} required />
            </>
          )}
          {step === 3 && (
            <>
              <input type="text" name="address" placeholder="Location / Address" onChange={handleChange} required />
              <input type="tel" name="phone" placeholder="Phone Number" onChange={handleChange} required />
            </>
          )}
          {step === 4 && (
            <>
              <input type="number" name="age" placeholder="Age" onChange={handleChange} required />
              <select name="sex" onChange={handleChange} required>
                <option value="">Select Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Other">Other</option>
              </select>
              <input type="file" name="profilePicture" accept="image/*" onChange={handleChange} />
            </>
          )}
          <div className="buttons">
            {step > 1 && <button type="button" onClick={handleBack}>Back</button>}
            {step < 5 && <button type="button" onClick={handleNext}>Next</button>}
            {step === 5 && <button type="submit">Submit</button>}
          </div>
        </form>
      </div>

      <div className="eco-artwork">
        <img
          src="/images/eco_hero_cartoon.jpg"
          alt="Eco Hero"
          className="eco-cartoon"
        />
        <div className="quote">“Be the change. Recycle and save Earth!”</div>
      </div>
    </div>
  );
};

export default RegisterForm;
