import React from 'react';
import { Outlet, Link } from 'react-router-dom';
import logo from '../assets/Main_logo.png';
import './Layout.css';

const Layout = () => (
  <>
    <header className="layout-header">
      <Link to="/dashboard" className="home-button">
        <img src={logo} alt="Home" className="home-icon" />
      </Link>
      <h1 className="app-title">
        Smart Waste Management & Recycling Tracker
      </h1>
    </header>
    <main className="layout-content">
      <Outlet />
    </main>
  </>
);

export default Layout;
