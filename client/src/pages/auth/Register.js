import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import { useTheme } from '../../contexts/ThemeContext';
import { SunIcon, MoonIcon, HomeIcon } from '@heroicons/react/24/outline';

const Register = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    role: 'ROLE_PATIENT', // Default to Patient
    phoneNumber: '',
    address: '',
    dateOfBirth: '',
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);
  const { register } = useAuth();
  const { theme, toggleTheme } = useTheme();
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const validateForm = () => {
    if (!formData.name.trim()) {
      setError('Full name is required');
      return false;
    }
    if (!formData.email.trim()) {
      setError('Email is required');
      return false;
    }
    if (!formData.phoneNumber.trim()) {
      setError('Phone number is required');
      return false;
    }
    if (!/^\d{10,15}$/.test(formData.phoneNumber.replace(/[\s\-()]/g, ''))) {
      setError('Please enter a valid phone number (10-15 digits)');
      return false;
    }
    if (!formData.address.trim()) {
      setError('Address is required');
      return false;
    }
    if (!formData.dateOfBirth) {
      setError('Date of birth is required');
      return false;
    }
    if (formData.password.length < 6) {
      setError('Password must be at least 6 characters long');
      return false;
    }
    if (formData.password !== formData.confirmPassword) {
      setError('Passwords do not match');
      return false;
    }
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    if (!validateForm()) {
      return;
    }

    setLoading(true);

    const { confirmPassword, ...registerData } = formData;
    const result = await register(registerData);
    
    if (result.success) {
      setSuccess('Registration successful! Redirecting to login...');
      setTimeout(() => {
        navigate('/login');
      }, 2000);
    } else {
      setError(result.message);
    }
    
    setLoading(false);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-background dark:bg-background-dark py-12 px-4 sm:px-6 lg:px-8">
      <Link
        to="/"
        className="fixed top-4 left-4 flex items-center gap-2 px-4 py-2 rounded-lg bg-surface dark:bg-surface-dark border border-border dark:border-border-dark hover:bg-primary/10 dark:hover:bg-primary-dark/10 hover:border-primary dark:hover:border-primary-dark transition-all shadow-lg group"
        aria-label="Go to Home"
      >
        <HomeIcon className="h-5 w-5 text-text-primary dark:text-text-primary-dark group-hover:text-primary dark:group-hover:text-primary-dark transition-colors" />
        <span className="text-sm font-medium text-text-primary dark:text-text-primary-dark group-hover:text-primary dark:group-hover:text-primary-dark transition-colors">
          Home
        </span>
      </Link>
      <button
        onClick={toggleTheme}
        className="fixed top-4 right-4 flex items-center justify-center w-12 h-12 rounded-lg bg-surface dark:bg-surface-dark border border-border dark:border-border-dark hover:bg-border/20 dark:hover:bg-border-dark/20 transition-colors shadow-lg"
        aria-label="Toggle Theme"
      >
        {theme === 'light' ? (
          <MoonIcon className="h-6 w-6 text-text-primary dark:text-text-primary-dark" />
        ) : (
          <SunIcon className="h-6 w-6 text-text-primary dark:text-text-primary-dark" />
        )}
      </button>
      <div className="max-w-md w-full space-y-8 card">
        <div>
          <h2 className="text-center text-3xl font-semibold text-text-primary dark:text-text-primary-dark">
            Create your account
          </h2>
          <p className="mt-2 text-center text-sm text-text-secondary dark:text-text-secondary-dark">
            Join our healthcare management system
          </p>
        </div>
        <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
          {error && (
            <div className="bg-error/10 dark:bg-error-dark/10 border border-error dark:border-error-dark text-error dark:text-error-dark px-4 py-3 rounded-lg" role="alert">
              <span className="block sm:inline text-sm">{error}</span>
            </div>
          )}
          {success && (
            <div className="bg-success/10 dark:bg-success-dark/10 border border-success dark:border-success-dark text-success dark:text-success-dark px-4 py-3 rounded-lg" role="alert">
              <span className="block sm:inline text-sm">{success}</span>
            </div>
          )}
          <div className="space-y-4">
            <div>
              <label htmlFor="name" className="input-label">
                Full Name
              </label>
              <input
                id="name"
                name="name"
                type="text"
                required
                className="input-field w-full"
                placeholder="John Doe"
                value={formData.name}
                onChange={handleChange}
              />
            </div>
            <div>
              <label htmlFor="email" className="input-label">
                Email address
              </label>
              <input
                id="email"
                name="email"
                type="email"
                autoComplete="email"
                required
                className="input-field w-full"
                placeholder="you@example.com"
                value={formData.email}
                onChange={handleChange}
              />
            </div>
            <div>
              <label htmlFor="phoneNumber" className="input-label">
                Phone Number
              </label>
              <input
                id="phoneNumber"
                name="phoneNumber"
                type="tel"
                required
                className="input-field w-full"
                placeholder="1234567890"
                value={formData.phoneNumber}
                onChange={handleChange}
              />
            </div>
            <div>
              <label htmlFor="address" className="input-label">
                Address
              </label>
              <textarea
                id="address"
                name="address"
                required
                rows="2"
                className="input-field w-full"
                placeholder="123 Main St, City, State, ZIP"
                value={formData.address}
                onChange={handleChange}
              />
            </div>
            <div>
              <label htmlFor="dateOfBirth" className="input-label">
                Date of Birth
              </label>
              <input
                id="dateOfBirth"
                name="dateOfBirth"
                type="date"
                required
                className="input-field w-full"
                value={formData.dateOfBirth}
                onChange={handleChange}
              />
            </div>
            <div>
              <label htmlFor="password" className="input-label">
                Password
              </label>
              <input
                id="password"
                name="password"
                type="password"
                autoComplete="new-password"
                required
                className="input-field w-full"
                placeholder="••••••••"
                value={formData.password}
                onChange={handleChange}
              />
            </div>
            <div>
              <label htmlFor="confirmPassword" className="input-label">
                Confirm Password
              </label>
              <input
                id="confirmPassword"
                name="confirmPassword"
                type="password"
                autoComplete="new-password"
                required
                className="input-field w-full"
                placeholder="••••••••"
                value={formData.confirmPassword}
                onChange={handleChange}
              />
            </div>
          </div>

          <div>
            <button
              type="submit"
              disabled={loading}
              className="btn-primary w-full"
            >
              {loading ? 'Registering...' : 'Register'}
            </button>
          </div>

          <div className="text-center">
            <p className="text-sm text-text-secondary dark:text-text-secondary-dark">
              Already have an account?{' '}
              <Link to="/login" className="font-medium text-primary dark:text-primary-dark hover:opacity-80">
                Sign in here
              </Link>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Register;
