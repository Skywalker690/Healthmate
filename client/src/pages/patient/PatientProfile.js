import React, { useState, useEffect } from 'react';
import DashboardLayout from '../../components/layout/DashboardLayout';
import { useAuth } from '../../contexts/AuthContext';
import { userService } from '../../services/userService';
import LoadingSpinner from '../../components/common/LoadingSpinner';

const PatientProfile = () => {
  const { updateUser } = useAuth();
  const [profileData, setProfileData] = useState({
    name: '',
    email: '',
    phoneNumber: '',
    address: '',
    dateOfBirth: '',
  });
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      const response = await userService.getCurrentUser();
      if (response.statusCode === 200 && response.user) {
        setProfileData({
          name: response.user.name || '',
          email: response.user.email || '',
          phoneNumber: response.user.phoneNumber || '',
          address: response.user.address || '',
          dateOfBirth: response.user.dateOfBirth || '',
        });
      }
    } catch (error) {
      console.error('Failed to fetch profile:', error);
      setError('Failed to load profile');
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    setProfileData({
      ...profileData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setSaving(true);

    try {
      const { email, ...updateData } = profileData;
      const response = await userService.updateCurrentUser(updateData);
      if (response.statusCode === 200) {
        setSuccess('Profile updated successfully');
        if (updateUser) updateUser(response.user);
      } else {
        setError(response.message || 'Failed to update profile');
      }
    } catch (error) {
      console.error('Failed to update profile:', error);
      setError('Failed to update profile');
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <DashboardLayout><LoadingSpinner /></DashboardLayout>;

  return (
    <DashboardLayout>
      <div>
        <h1 className="text-3xl font-bold text-gray-900 dark:text-gray-100 mb-8">My Profile</h1>

        {/* Error Alert */}
        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4
                          dark:bg-red-900 dark:border-red-700 dark:text-red-200">
            {error}
          </div>
        )}

        {/* Success Alert */}
        {success && (
          <div className="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4
                          dark:bg-green-900 dark:border-green-700 dark:text-green-200">
            {success}
          </div>
        )}

        <div className="bg-white dark:bg-gray-900 rounded-lg shadow-md p-6">
          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <label htmlFor="name" className="block text-sm font-medium text-gray-700 dark:text-gray-300">
                Full Name
              </label>
              <input
                type="text"
                name="name"
                id="name"
                required
                value={profileData.name}
                onChange={handleChange}
                className="mt-1 block w-full px-3 py-2 border border-gray-300 dark:border-gray-700
                           rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500
                           dark:bg-gray-800 dark:text-gray-100"
              />
            </div>

            <div>
              <label htmlFor="email" className="block text-sm font-medium text-gray-700 dark:text-gray-300">
                Email Address
              </label>
              <input
                type="email"
                name="email"
                id="email"
                value={profileData.email}
                readOnly
                disabled
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm
                           bg-gray-100 text-gray-600 cursor-not-allowed dark:bg-gray-800 dark:text-gray-400"
                title="Email cannot be changed"
              />
              <p className="mt-1 text-xs text-gray-500 dark:text-gray-400">Email address cannot be changed</p>
            </div>

            <div>
              <label htmlFor="phoneNumber" className="block text-sm font-medium text-gray-700 dark:text-gray-300">
                Phone Number
              </label>
              <input
                type="tel"
                name="phoneNumber"
                id="phoneNumber"
                required
                value={profileData.phoneNumber}
                onChange={handleChange}
                className="mt-1 block w-full px-3 py-2 border border-gray-300 dark:border-gray-700
                           rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500
                           dark:bg-gray-800 dark:text-gray-100"
              />
            </div>

            <div>
              <label htmlFor="address" className="block text-sm font-medium text-gray-700 dark:text-gray-300">
                Address
              </label>
              <textarea
                name="address"
                id="address"
                required
                rows="3"
                value={profileData.address}
                onChange={handleChange}
                className="mt-1 block w-full px-3 py-2 border border-gray-300 dark:border-gray-700
                           rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500
                           dark:bg-gray-800 dark:text-gray-100"
              />
            </div>

            <div>
              <label htmlFor="dateOfBirth" className="block text-sm font-medium text-gray-700 dark:text-gray-300">
                Date of Birth
              </label>
              <input
                type="date"
                name="dateOfBirth"
                id="dateOfBirth"
                required
                value={profileData.dateOfBirth}
                onChange={handleChange}
                className="mt-1 block w-full px-3 py-2 border border-gray-300 dark:border-gray-700
                           rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500
                           dark:bg-gray-800 dark:text-gray-100"
              />
            </div>

            <div>
              <button
                type="submit"
                disabled={saving}
                className="w-full bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-md
                           transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
              >
                {saving ? 'Saving...' : 'Save Changes'}
              </button>
            </div>
          </form>
        </div>
      </div>
    </DashboardLayout>
  );
};

export default PatientProfile;
