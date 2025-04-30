import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './HistoryPage.css';

const HistoryPage = () => {
  const navigate = useNavigate();
  const [scannedHistory, setScannedHistory] = useState([]);
  const [aiHistory, setAiHistory]         = useState([]);
  const [manualHistory, setManualHistory] = useState([]);
  const [loading, setLoading]             = useState(true);
  const [error, setError]                 = useState('');

  useEffect(() => {
    const token   = localStorage.getItem('token');
    const headers = { Authorization: `Bearer ${token}` };

    Promise.all([
      axios.get('http://localhost:8080/api/dashboard/barcode/recycled',    { headers }),
      axios.get('http://localhost:8080/api/dashboard/barcode/ai/history',  { headers }),
      axios.get('http://localhost:8080/api/dashboard/barcode/manual',      { headers })
    ])
      .then(([scanRes, aiRes, manualRes]) => {
        setScannedHistory(scanRes.data);
        setAiHistory(aiRes.data);
        setManualHistory(manualRes.data);
      })
      .catch(err => {
        console.error(err);
        setError('Failed to load history.');
      })
      .finally(() => setLoading(false));
  }, []);

  const formatDate = ts => {
    const d = new Date(ts);
    return isNaN(d) ? ts : d.toLocaleString();
  };

  if (loading) return <p>Loading history…</p>;
  if (error)   return <p className="error-msg">{error}</p>;

  return (
    <div className="history-container">
      <h2>📜 Your Recycling History</h2>

      <div className="history-sections">
        {/* Barcode Scan History */}
        <section className="history-section">
          <h3>📦 Barcode Scan</h3>
          {scannedHistory.length ? (
            <div className="table-scroll">
              <table className="history-table">
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Barcode</th>
                    <th>Result</th>
                  </tr>
                </thead>
                <tbody>
                  {scannedHistory.map(item => {
                    const label = item.predictedLabel || item.classification || '—';
                    return (
                      <tr key={item.id}>
                        <td>{formatDate(item.timestamp)}</td>
                        <td>{item.barcode}</td>
                        <td>{label}</td>
                      </tr>
                    );
                  })}
                </tbody>
              </table>
            </div>
          ) : (
            <p>No barcode scans yet.</p>
          )}
        </section>

        {/* AI‐Classified History */}
        <section className="history-section">
          <h3>🤖 AI Classified</h3>
          {aiHistory.length ? (
            <div className="table-scroll">
              <table className="history-table">
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Label</th>
                    <th>Confidence</th>
                  </tr>
                </thead>
                <tbody>
                  {aiHistory.map(({ id, predictedLabel, confidence, timestamp }) => (
                    <tr key={id}>
                      <td>{formatDate(timestamp)}</td>
                      <td>{predictedLabel}</td>
                      <td>{(confidence * 100).toFixed(1)}%</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ) : (
            <p>No AI classifications yet.</p>
          )}
        </section>

        {/* Manual Entry History */}
        <section className="history-section">
          <h3>✍️ Manual Entries</h3>
          {manualHistory.length ? (
            <div className="table-scroll">
              <table className="history-table">
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Description</th>
                  </tr>
                </thead>
                <tbody>
                  {manualHistory.map(({ id, name, category, description, timestamp }) => (
                    <tr key={id}>
                      <td>{formatDate(timestamp)}</td>
                      <td>{name}</td>
                      <td>{category}</td>
                      <td>{description}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ) : (
            <p>No manual entries yet.</p>
          )}
        </section>
      </div>

      <button className="btn cancel-btn" onClick={() => navigate('/dashboard')}>
        ← Back to Dashboard
      </button>
    </div>
  );
};

export default HistoryPage;
