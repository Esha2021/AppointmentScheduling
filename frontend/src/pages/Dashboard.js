import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { appointmentAPI } from '../services/api';
import './Dashboard.css';

function Dashboard() {
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchAppointments();
  }, []);

  const fetchAppointments = async () => {
    try {
      setLoading(true);
      const response = await appointmentAPI.getAllAppointments();
      const username = localStorage.getItem('username');

      // Filter appointments to show only current user's appointments
      const userAppointments = (response.data || []).filter(
        appointment => appointment.userName === username
      );

      setAppointments(userAppointments);
    } catch (err) {
      setError('Failed to fetch appointments. Please try again.');
      console.error('Error fetching appointments:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleBookAppointment = () => {
    navigate('/book-appointment');
  };

  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <h1>📅 Appointment Dashboard</h1>
        <button onClick={handleBookAppointment} className="book-btn">
          + Book New Appointment
        </button>
      </div>

      {error && <div className="error-message">{error}</div>}

      <div className="appointments-section">
        <h2>Your Appointments</h2>

        {loading ? (
          <div className="loading">Loading appointments...</div>
        ) : appointments.length === 0 ? (
          <div className="no-appointments">
            <p>No appointments yet. <button onClick={handleBookAppointment} className="link-btn">Book one now!</button></p>
          </div>
        ) : (
          <div className="appointments-grid">
            {appointments.map((appointment) => (
              <div key={appointment.id} className="appointment-card">
                <div className="appointment-header">
                  <div>
                    <h3>{appointment.appointeeName}</h3>
                    <p className="appointment-subtitle">with {appointment.providerName}</p>
                  </div>
                  <span className={`status status-${appointment.status?.toLowerCase()}`}>
                    {appointment.status}
                  </span>
                </div>

                <div className="appointment-details">
                  <div className="detail-row">
                    <span className="label">📧 Email:</span>
                    <span className="value">{appointment.appointeeEmailId}</span>
                  </div>

                  <div className="detail-row">
                    <span className="label">👤 User:</span>
                    <span className="value">{appointment.userName}</span>
                  </div>

                  <div className="detail-row">
                    <span className="label">🏥 Provider:</span>
                    <span className="value">{appointment.providerName}</span>
                  </div>

                  <div className="detail-row">
                    <span className="label">📅 Date & Time:</span>
                    <span className="value">
                      {new Date(appointment.datetime).toLocaleString()}
                    </span>
                  </div>

                  <div className="detail-row">
                    <span className="label">📍 Location:</span>
                    <span className="value">{appointment.location}</span>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default Dashboard;
