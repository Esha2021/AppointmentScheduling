import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import './AdminDashboard.css';

function AdminDashboard() {
  const [providers, setProviders] = useState([]);
  const [analytics, setAnalytics] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [formData, setFormData] = useState({
    scheduleProviderName: '',
    emailId: '',
    description: '',
  });
  const navigate = useNavigate();

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      const [providersRes, analyticsRes] = await Promise.all([
        api.get('/admin/providers'),
        api.get('/admin/analytics'),
      ]);
      setProviders(providersRes.data || []);
      setAnalytics(analyticsRes.data || {});
      setError('');
    } catch (err) {
      setError('Failed to load admin data. You may not have admin privileges.');
      console.error('Error:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleCreateProvider = async (e) => {
    e.preventDefault();
    if (!formData.scheduleProviderName || !formData.emailId) {
      setError('Please fill in all required fields');
      return;
    }

    try {
      await api.post('/admin/providers/create', formData);
      setSuccess('Provider created successfully!');
      setFormData({ scheduleProviderName: '', emailId: '', description: '' });
      setShowCreateForm(false);
      fetchData();
    } catch (err) {
      setError('Failed to create provider');
      console.error('Error:', err);
    }
  };

  const handleDeleteProvider = async (id) => {
    if (window.confirm('Are you sure you want to delete this provider?')) {
      try {
        await api.delete(`/admin/providers/${id}`);
        setSuccess('Provider deleted successfully!');
        fetchData();
      } catch (err) {
        setError('Failed to delete provider');
      }
    }
  };

  if (loading) {
    return <div className="admin-container"><p>Loading admin dashboard...</p></div>;
  }

  return (
    <div className="admin-container">
      <div className="admin-header">
        <h1>👨‍💼 Admin Dashboard</h1>
        <button onClick={() => navigate('/dashboard')} className="back-to-user-btn">
          ← Back to User Dashboard
        </button>
      </div>

      {error && <div className="error-message">{error}</div>}
      {success && <div className="success-message">{success}</div>}

      {/* Analytics Section */}
      {analytics && (
        <div className="analytics-section">
          <h2>📊 Analytics</h2>
          <div className="stats-grid">
            <div className="stat-card">
              <div className="stat-value">{analytics.totalAppointments || 0}</div>
              <div className="stat-label">Total Appointments</div>
            </div>
            <div className="stat-card">
              <div className="stat-value">{analytics.totalProviders || 0}</div>
              <div className="stat-label">Service Providers</div>
            </div>
            <div className="stat-card">
              <div className="stat-value">{analytics.totalUsers || 0}</div>
              <div className="stat-label">Total Users</div>
            </div>
          </div>
        </div>
      )}

      {/* Providers Management Section */}
      <div className="providers-section">
        <div className="section-header">
          <h2>🏥 Manage Providers</h2>
          <button
            onClick={() => setShowCreateForm(!showCreateForm)}
            className="btn-primary"
          >
            {showCreateForm ? '✕ Cancel' : '+ Add Provider'}
          </button>
        </div>

        {showCreateForm && (
          <div className="create-form">
            <h3>Create New Provider</h3>
            <form onSubmit={handleCreateProvider}>
              <div className="form-group">
                <label>Provider Name *</label>
                <input
                  type="text"
                  name="scheduleProviderName"
                  value={formData.scheduleProviderName}
                  onChange={handleChange}
                  placeholder="e.g., Dr. John Smith"
                  required
                />
              </div>

              <div className="form-group">
                <label>Email *</label>
                <input
                  type="email"
                  name="emailId"
                  value={formData.emailId}
                  onChange={handleChange}
                  placeholder="doctor@hospital.com"
                  required
                />
              </div>

              <div className="form-group">
                <label>Description</label>
                <textarea
                  name="description"
                  value={formData.description}
                  onChange={handleChange}
                  placeholder="e.g., Cardiologist with 10+ years experience"
                  rows="3"
                />
              </div>

              <button type="submit" className="btn-success">Create Provider</button>
            </form>
          </div>
        )}

        <div className="providers-list">
          {providers.length === 0 ? (
            <p>No providers found. Create one to get started!</p>
          ) : (
            providers.map(provider => (
              <div key={provider.id} className="provider-card">
                <div className="provider-header">
                  <h3>{provider.scheduleProviderName}</h3>
                  <span className="provider-id">ID: {provider.id}</span>
                </div>

                <div className="provider-details">
                  <p><strong>📧 Email:</strong> {provider.emailId}</p>
                  <p><strong>📝 Description:</strong> {provider.description || 'N/A'}</p>
                  <p><strong>📅 Total Appointments:</strong> {provider.totalAppointments}</p>
                </div>

                <div className="provider-actions">
                  <button
                    onClick={() => navigate(`/timeslots/${provider.id}`)}
                    className="btn-info"
                  >
                    ⏰ Manage Slots
                  </button>
                  <button
                    onClick={() => handleDeleteProvider(provider.id)}
                    className="btn-danger"
                  >
                    🗑️ Delete
                  </button>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
}

export default AdminDashboard;
