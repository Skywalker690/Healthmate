import React, { useState } from 'react';
import Modal from './Modal';
import { userService } from '../../services/userService';
import { LockClosedIcon } from '@heroicons/react/24/outline';

const ChangePassword = ({ isOpen, onClose, onSuccess }) => {
  const [formData, setFormData] = useState({
    oldPassword: '',
    newPassword: '',
    confirmPassword: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    // Validation
    if (formData.newPassword !== formData.confirmPassword) {
      setError('New passwords do not match');
      return;
    }

    if (formData.newPassword.length < 6) {
      setError('New password must be at least 6 characters long');
      return;
    }

    if (formData.oldPassword === formData.newPassword) {
      setError('New password must be different from old password');
      return;
    }

    setLoading(true);

    try {
      const response = await userService.changePassword(
        formData.oldPassword,
        formData.newPassword
      );

      if (response.statusCode === 200) {
        // Reset form
        setFormData({
          oldPassword: '',
          newPassword: '',
          confirmPassword: '',
        });
        onSuccess && onSuccess('Password changed successfully');
        onClose();
      } else {
        setError(response.message || 'Failed to change password');
      }
    } catch (error) {
      console.error('Failed to change password:', error);
      setError(error.response?.data?.message || 'Failed to change password. Please check your current password.');
    } finally {
      setLoading(false);
    }
  };

  const handleClose = () => {
    setFormData({
      oldPassword: '',
      newPassword: '',
      confirmPassword: '',
    });
    setError('');
    onClose();
  };

  return (
    <Modal isOpen={isOpen} onClose={handleClose} title="Change Password">
      <form onSubmit={handleSubmit} className="space-y-4">
        {error && (
          <div className="bg-error/10 dark:bg-error-dark/10 border border-error dark:border-error-dark text-error dark:text-error-dark px-4 py-3 rounded-lg text-sm">
            {error}
          </div>
        )}

        <div>
          <label className="input-label">Current Password</label>
          <div className="relative">
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <LockClosedIcon className="h-5 w-5 text-text-secondary dark:text-text-secondary-dark" />
            </div>
            <input
              type="password"
              value={formData.oldPassword}
              onChange={(e) =>
                setFormData({ ...formData, oldPassword: e.target.value })
              }
              className="input-field w-full pl-10"
              placeholder="Enter current password"
              required
            />
          </div>
        </div>

        <div>
          <label className="input-label">New Password</label>
          <div className="relative">
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <LockClosedIcon className="h-5 w-5 text-text-secondary dark:text-text-secondary-dark" />
            </div>
            <input
              type="password"
              value={formData.newPassword}
              onChange={(e) =>
                setFormData({ ...formData, newPassword: e.target.value })
              }
              className="input-field w-full pl-10"
              placeholder="Enter new password"
              required
              minLength="6"
            />
          </div>
          <p className="text-xs text-text-secondary dark:text-text-secondary-dark mt-1">
            Must be at least 6 characters long
          </p>
        </div>

        <div>
          <label className="input-label">Confirm New Password</label>
          <div className="relative">
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <LockClosedIcon className="h-5 w-5 text-text-secondary dark:text-text-secondary-dark" />
            </div>
            <input
              type="password"
              value={formData.confirmPassword}
              onChange={(e) =>
                setFormData({ ...formData, confirmPassword: e.target.value })
              }
              className="input-field w-full pl-10"
              placeholder="Confirm new password"
              required
              minLength="6"
            />
          </div>
        </div>

        <div className="flex justify-end space-x-3 pt-4">
          <button
            type="button"
            onClick={handleClose}
            className="btn-outline"
            disabled={loading}
          >
            Cancel
          </button>
          <button
            type="submit"
            className="btn-primary"
            disabled={loading}
          >
            {loading ? 'Changing...' : 'Change Password'}
          </button>
        </div>
      </form>
    </Modal>
  );
};

export default ChangePassword;
