import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import WelcomePage from './pages/WelcomePage';
import RegisterForm from './pages/RegisterForm';
import LoginForm from './pages/LoginForm';
import Dashboard from './pages/Dashboard';

import Layout from './pages/Layout';
import ProfilePage from './pages/ProfilePage';
import EditProfilePage from './pages/EditProfilePage';
import ProfilePicturePage from './pages/ProfilePicturePage';
import EditProfilePicturePage from './pages/EditProfilePicturePage';

import ClassificationPage from './pages/ClassificationPage';
import ManualEntryPage from './pages/ManualEntryPage';
import HistoryPage from './pages/HistoryPage';
import RecyclingCentersPage from './pages/RecyclingCentersPage';
import AlertsPage      from './pages/AlertsPage';

const App = () => (
  <Router>
    <Routes>
      <Route element={<Layout />}>
      <Route path="/" element={<WelcomePage />} />
      <Route path="/register" element={<RegisterForm />} />
      <Route path="/login" element={<LoginForm />} />
      <Route path="/dashboard" element={<Dashboard />} />

      {/* Profile flow */}
      <Route path="/profile" element={<ProfilePage />} />
      <Route path="/edit-profile" element={<EditProfilePage />} />
     <Route path="/profile-picture" element={<ProfilePicturePage />} />
      <Route path="/edit-profile-picture" element={<EditProfilePicturePage />} />
      <Route path="/classification" element={<ClassificationPage />} />
      <Route path="/manual-entry" element={<ManualEntryPage />} />
      <Route path="/history"       element={<HistoryPage />} />
      <Route path="/recycling-centers" element={<RecyclingCentersPage />} />
      <Route path="/alerts" element={<AlertsPage />} />
      </Route>
    </Routes>
  </Router>
);

export default App;
