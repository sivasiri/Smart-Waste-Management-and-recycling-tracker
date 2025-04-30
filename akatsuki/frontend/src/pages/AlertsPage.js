import React, { useState, useEffect } from 'react';
import { useNavigate }       from 'react-router-dom';
import axios                 from 'axios';
import './AlertsPage.css';

const DAYS_OF_WEEK = [
  { label: 'Monday',    value: 'MO' },
  { label: 'Tuesday',   value: 'TU' },
  { label: 'Wednesday', value: 'WE' },
  { label: 'Thursday',  value: 'TH' },
  { label: 'Friday',    value: 'FR' },
  { label: 'Saturday',  value: 'SA' },
  { label: 'Sunday',    value: 'SU' },
];

const AlertsPage = () => {
  const navigate = useNavigate();

  // existing alert‐send state
  const [method, setMethod]           = useState('email');
  const [logs, setLogs]               = useState([]);
  const [loadingLogs, setLoadingLogs] = useState(true);
  const [sending, setSending]         = useState(false);
  const [feedback, setFeedback]       = useState('');

  // new scheduling state
  const [days, setDays]               = useState([]);
  const [time, setTime]               = useState('07:00');
  const [scheduling, setScheduling]   = useState(false);
  const [schedFeedback, setSchedFeedback] = useState('');

  // fetch existing logs
  const fetchLogs = async () => {
    setLoadingLogs(true);
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(
        'http://localhost:8080/api/dashboard/alerts/logs',
        { headers: { Authorization: `Bearer ${token}` } }
      );
      console.log('🚨 raw alert logs:', res.data);
      setLogs(res.data);
    } catch (err) {
      console.error(err);
      setFeedback('❌ Failed to load logs.');
    } finally {
      setLoadingLogs(false);
    }
  };

  useEffect(() => {
    fetchLogs();
  }, []);

  // send a one-off alert
  const handleSend = async () => {
    setSending(true);
    setFeedback('');
    try {
      const token = localStorage.getItem('token');
      await axios.post(
        `http://localhost:8080/api/dashboard/alerts/send?method=${method}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setFeedback(`✅ Alert sent via ${method.toUpperCase()}!`);
      await fetchLogs();
    } catch (err) {
      console.error(err);
      setFeedback('❌ Failed to send alert.');
    } finally {
      setSending(false);
    }
  };

  // schedule a recurring pickup reminder
  const handleSchedule = async () => {
    if (!days.length) {
      setSchedFeedback('❌ Pick at least one day.');
      return;
    }
    setScheduling(true);
    setSchedFeedback('');
    try {
      const token = localStorage.getItem('token');
      await axios.post(
        'http://localhost:8080/api/dashboard/alerts/schedule',
        { days, time },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setSchedFeedback('⏰ Pickup reminder scheduled!');
    } catch (err) {
      console.error(err);
      setSchedFeedback('❌ Scheduling failed.');
    } finally {
      setScheduling(false);
    }
  };

  // safe log formatters
  const extractDate = log => {
    const cands = [log.timestamp, log.date, log.createdAt, log.sentAt];
    for (let v of cands) {
      if (v) {
        const d = new Date(v);
        return isNaN(d) ? v : d.toLocaleString();
      }
    }
    return '—';
  };
  const extractMethod = log => String(log.method ?? log.type ?? '—').toUpperCase();
  const extractStatus = log => String(log.status ?? log.result ?? '—');

  return (
    <div className="alerts-container">
      <h2>🚨 Garbage Collection Alerts</h2>

      {/* one-off send */}
      {feedback && <p className="feedback">{feedback}</p>}
      <div className="send-section">
        <label>
          <input
            type="radio"
            value="email"
            checked={method === 'email'}
            onChange={() => setMethod('email')}
          />
          Email
        </label>
        <label>
          <input
            type="radio"
            value="sms"
            checked={method === 'sms'}
            onChange={() => setMethod('sms')}
          />
          SMS
        </label>
        <button
          className="btn send-btn"
          onClick={handleSend}
          disabled={sending}
        >
          {sending ? 'Sending…' : 'Send Alert'}
        </button>
      </div>

      {/* scheduling */}
      <h3>⏰ Schedule Pickup Reminder</h3>
      {schedFeedback && <p className="feedback">{schedFeedback}</p>}
      <div className="schedule-section">
        <div className="days-select">
          {DAYS_OF_WEEK.map(d => (
            <label key={d.value}>
              <input
                type="checkbox"
                value={d.value}
                checked={days.includes(d.value)}
                onChange={e => {
                  const v = e.target.value;
                  setDays(ds =>
                    ds.includes(v) ? ds.filter(x => x !== v) : [...ds, v]
                  );
                }}
              />
              {d.label}
            </label>
          ))}
        </div>
        <div className="time-input">
          <label>
            Reminder Time:&nbsp;
            <input
              type="time"
              value={time}
              onChange={e => setTime(e.target.value)}
            />
          </label>
        </div>
        <button
          className="btn schedule-btn"
          onClick={handleSchedule}
          disabled={scheduling}
        >
          {scheduling ? 'Scheduling…' : 'Schedule Reminder'}
        </button>
      </div>

      {/* logs */}
      <h3>📋 Alert Logs</h3>
      {loadingLogs ? (
        <p>Loading logs…</p>
      ) : logs.length ? (
        <table className="logs-table">
          <thead>
            <tr>
              <th>Date</th>
              <th>Method</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {logs.map(rawLog => {
              const date    = extractDate(rawLog);
              const mtd     = extractMethod(rawLog);
              const stat    = extractStatus(rawLog);
              const key     = rawLog.id ?? `${mtd}-${date}`;
              return (
                <tr key={key}>
                  <td>{date}</td>
                  <td>{mtd}</td>
                  <td>{stat}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      ) : (
        <p>No alerts sent yet.</p>
      )}

      <button
        className="btn cancel-btn"
        onClick={() => navigate('/dashboard')}
      >
        ← Back to Dashboard
      </button>
    </div>
  );
};

export default AlertsPage;
