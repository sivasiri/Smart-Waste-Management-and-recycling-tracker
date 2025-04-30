import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate }      from 'react-router-dom';
import axios                from 'axios';
import './RecyclingCentersPage.css';

const DEFAULT_RADIUS = 5000;
const CENTER_TYPES   = ['all','plastic','metal','paper','glass','cardboard'];

export default function RecyclingCentersPage() {
  const navigate = useNavigate();
  const [location, setLocation]     = useState(null);
  const [centers, setCenters]       = useState([]);
  const [loadingLoc, setLoadingLoc] = useState(true);
  const [loadingCen, setLoadingCen] = useState(false);
  const [error, setError]           = useState('');
  const [typeFilter, setTypeFilter] = useState('all');
  const [radius, setRadius]         = useState(DEFAULT_RADIUS);

  const requestLocation = useCallback(() => {
    setLoadingLoc(true);
    setError('');
    if (!navigator.geolocation) {
      setError('Geolocation not supported');
      setLoadingLoc(false);
      return;
    }
    navigator.geolocation.getCurrentPosition(
      pos => {
        setLocation({ lat: pos.coords.latitude, lng: pos.coords.longitude });
        setLoadingLoc(false);
      },
      () => {
        setError('Failed to get location');
        setLoadingLoc(false);
      }
    );
  }, []);

  useEffect(() => {
    requestLocation();
  }, [requestLocation]);

  const fetchCenters = useCallback(async () => {
    if (!location) return;
    setLoadingCen(true);
    setError('');
    try {
      const token = localStorage.getItem('token');
      const params = {
        latitude:  location.lat,
        longitude: location.lng,
        radiusMeters: radius
      };
      if (typeFilter !== 'all') params.type = typeFilter;
      const res = await axios.get(
        'http://localhost:8080/api/dashboard/recycling-centers/nearby',
        { headers: { Authorization: `Bearer ${token}` }, params }
      );
      setCenters(res.data);
    } catch {
      setError('Failed to load centers');
    } finally {
      setLoadingCen(false);
    }
  }, [location, typeFilter, radius]);

  useEffect(() => {
    fetchCenters();
  }, [fetchCenters]);

  useEffect(() => {
    if (!location || !window.google || !window.google.maps) return;

    const map = new window.google.maps.Map(
      document.getElementById('google-map'),
      { center: location, zoom: 13 }
    );

    new window.google.maps.Marker({
      position: location,
      map,
      title: 'You'
    });

    centers.forEach(c => {
      new window.google.maps.Marker({
        position: {
          lat: c.location.coordinates[1],
          lng: c.location.coordinates[0]
        },
        map,
        title: c.name
      });
    });

    // <-- No cleanup function returned here
  }, [location, centers]);

  if (loadingLoc) {
    return (
      <div className="locator-container">
        <p>Getting your location…</p>
        <button className="btn" onClick={requestLocation}>🔄 Retry</button>
        <button className="btn cancel-btn" onClick={() => navigate('/dashboard')}>
          ← Back
        </button>
      </div>
    );
  }

  if (error && !location) {
    return (
      <div className="locator-container">
        <p className="error-msg">{error}</p>
        <button className="btn" onClick={requestLocation}>🔄 Retry</button>
        <button className="btn cancel-btn" onClick={() => navigate('/dashboard')}>
          ← Back
        </button>
      </div>
    );
  }

  if (loadingCen) {
    return <p>Loading centers…</p>;
  }

  return (
    <div className="locator-container">
      <h2>📍 Recycling Center Locator</h2>
      <div className="locator-controls">
        {/* filters… */}
      </div>
      <div className="locator-main">
        <div id="google-map" className="locator-map" />
        <div className="locator-list">
          {/* list… */}
        </div>
      </div>
      <button className="btn cancel-btn" onClick={() => navigate('/dashboard')}>
        ← Back to Dashboard
      </button>
    </div>
  );
}
