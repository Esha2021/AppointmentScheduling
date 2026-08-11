import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { appointmentAPI, providerAPI, timeSlotAPI } from '../services/api';
import './BookAppointment.css';

function BookAppointment() {
  const [formData, setFormData] = useState({
    appointeeName: '',
    appointeeEmailId: '',
    datetime: '',
    location: '',
    scheduleProviderId: '',
  });
  const [providers, setProviders] = useState([]);
  const [providersLoading, setProvidersLoading] = useState(true);
  const [timeSlots, setTimeSlots] = useState([]);
  const [slotsLoading, setSlotsLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    fetchProviders();
  }, []);

  const fetchProviders = async () => {
    try {
      const response = await providerAPI.getAllProviders();
      setProviders(response.data || []);
      if (response.data && response.data.length > 0) {
        setFormData(prev => ({ ...prev, scheduleProviderId: response.data[0].id.toString() }));
      }
    } catch (err) {
      console.error('Failed to fetch providers:', err);
      setError('Failed to load providers. Please try again.');
    } finally {
      setProvidersLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));

    // Fetch available time slots when provider is selected
    if (name === 'scheduleProviderId' && value) {
      fetchTimeSlots(value);
    }
  };

  const fetchTimeSlots = async (providerId) => {
    try {
      setSlotsLoading(true);
      const response = await timeSlotAPI.getAvailableSlots(providerId);
      setTimeSlots(response.data || []);
      setFormData(prev => ({ ...prev, datetime: '' })); // Reset datetime
    } catch (err) {
      console.error('Failed to fetch time slots:', err);
      setTimeSlots([]);
    } finally {
      setSlotsLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    if (!formData.appointeeName || !formData.appointeeEmailId || !formData.datetime || !formData.location) {
      setError('Please fill in all fields.');
      return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.appointeeEmailId)) {
      setError('Please enter a valid email address.');
      return;
    }

    setLoading(true);

    try {
      const userId = parseInt(localStorage.getItem('userId') || '1');
      const scheduleProviderId = parseInt(formData.scheduleProviderId);

      await appointmentAPI.bookAppointment(
        userId,
        scheduleProviderId,
        formData.appointeeName,
        formData.appointeeEmailId,
        formData.datetime,
        formData.location
      );

      setSuccess('Appointment booked successfully!');
      setFormData({
        appointeeName: '',
        appointeeEmailId: '',
        datetime: '',
        location: '',
        scheduleProviderId: '1',
      });

      setTimeout(() => {
        navigate('/dashboard');
      }, 2000);
    } catch (err) {
      const errorMsg = err.response?.data?.message || err.response?.data || 'Failed to book appointment.';
      setError(`Error: ${errorMsg}`);
      console.error('Booking error:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="book-appointment-container">
      <div className="form-wrapper">
        <div className="booking-form">
          <h1>📅 Book an Appointment</h1>

          {error && <div className="error-message">{error}</div>}
          {success && <div className="success-message">{success}</div>}

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="appointeeName">Appointment Name *</label>
              <input
                type="text"
                id="appointeeName"
                name="appointeeName"
                value={formData.appointeeName}
                onChange={handleChange}
                required
                placeholder="e.g., Team Meeting, Doctor Checkup"
              />
            </div>

            <div className="form-group">
              <label htmlFor="appointeeEmailId">Email Address *</label>
              <input
                type="email"
                id="appointeeEmailId"
                name="appointeeEmailId"
                value={formData.appointeeEmailId}
                onChange={handleChange}
                required
                placeholder="your.email@example.com"
              />
            </div>

            <div className="form-group">
              <label htmlFor="datetime">Available Time Slots *</label>
              {!formData.scheduleProviderId ? (
                <p className="error-text">Please select a provider first</p>
              ) : slotsLoading ? (
                <p>Loading available slots...</p>
              ) : timeSlots.length === 0 ? (
                <p className="error-text">No available time slots for this provider</p>
              ) : (
                <select
                  id="datetime"
                  name="datetime"
                  value={formData.datetime}
                  onChange={handleChange}
                  required
                >
                  <option value="">-- Select a time slot --</option>
                  {timeSlots.map(slot => (
                    <option key={slot.id} value={slot.startTime}>
                      {new Date(slot.startTime).toLocaleString()} - {new Date(slot.endTime).toLocaleTimeString()}
                    </option>
                  ))}
                </select>
              )}
            </div>

            <div className="form-group">
              <label htmlFor="location">Location *</label>
              <input
                type="text"
                id="location"
                name="location"
                value={formData.location}
                onChange={handleChange}
                required
                placeholder="Meeting room, address, or video call link"
              />
            </div>

            <div className="form-group">
              <label htmlFor="scheduleProviderId">Select Service Provider *</label>
              {providersLoading ? (
                <p>Loading providers...</p>
              ) : providers.length === 0 ? (
                <p className="error-text">No providers available</p>
              ) : (
                <select
                  id="scheduleProviderId"
                  name="scheduleProviderId"
                  value={formData.scheduleProviderId}
                  onChange={handleChange}
                  required
                >
                  <option value="">-- Select a provider --</option>
                  {providers.map(provider => (
                    <option key={provider.id} value={provider.id}>
                      {provider.scheduleProviderName} ({provider.emailId})
                    </option>
                  ))}
                </select>
              )}
            </div>

            <button type="submit" className="submit-btn" disabled={loading}>
              {loading ? 'Booking...' : 'Book Appointment'}
            </button>
          </form>

          <div className="form-footer">
            <button
              type="button"
              onClick={() => navigate('/dashboard')}
              className="back-btn"
            >
              ← Back to Dashboard
            </button>
          </div>
        </div>
      </div>

      <div className="info-section">
        <h2>📋 Appointment Details</h2>
        <div className="info-box">
          <h3>What to Include</h3>
          <ul>
            <li><strong>Appointment Name:</strong> Title of your appointment</li>
            <li><strong>Email:</strong> Your email address for confirmation</li>
            <li><strong>Date & Time:</strong> When you want to schedule</li>
            <li><strong>Location:</strong> Where the meeting will take place</li>
          </ul>
        </div>

        <div className="info-box">
          <h3>💡 Tips</h3>
          <ul>
            <li>Double-check the date and time before submitting</li>
            <li>Include specific location details for clarity</li>
            <li>You can use the link from your dashboard to view all appointments</li>
          </ul>
        </div>
      </div>
    </div>
  );
}

export default BookAppointment;
