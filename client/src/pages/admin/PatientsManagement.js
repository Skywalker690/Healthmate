import React, { useState, useEffect } from 'react';
import DashboardLayout from '../../components/layout/DashboardLayout';
import { patientService } from '../../services/patientService';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Modal from '../../components/common/Modal';
import { EyeIcon, TrashIcon } from '@heroicons/react/24/outline';

const PatientsManagement = () => {
  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [isProfileModalOpen, setIsProfileModalOpen] = useState(false);
  const [viewingPatient, setViewingPatient] = useState(null);

  useEffect(() => {
    fetchPatients();
  }, []);

  const fetchPatients = async () => {
    try {
      setLoading(true);
      const response = await patientService.getAllPatients();
      if (response.statusCode === 200) {
        setPatients(response.patientList || []);
      }
    } catch (error) {
      console.error('Failed to fetch patients:', error);
      setError('Failed to load patients');
    } finally {
      setLoading(false);
    }
  };

  const handleViewProfile = async (id) => {
    try {
      setLoading(true);
      const response = await patientService.getPatientById(id);
      if (response.statusCode === 200) {
        setViewingPatient(response.patient);
        setIsProfileModalOpen(true);
      }
    } catch (error) {
      console.error('Failed to fetch patient profile:', error);
      setError('Failed to load patient profile');
      setTimeout(() => setError(''), 3000);
    } finally {
      setLoading(false);
    }
  };

  const handleDeletePatient = async (id) => {
    if (!window.confirm('Are you sure you want to delete this patient?')) {
      return;
    }

    try {
      const response = await patientService.deletePatient(id);
      if (response.statusCode === 200) {
        setSuccess('Patient deleted successfully');
        fetchPatients();
        setTimeout(() => setSuccess(''), 3000);
      }
    } catch (error) {
      console.error('Failed to delete patient:', error);
      setError('Failed to delete patient');
      setTimeout(() => setError(''), 3000);
    }
  };

  if (loading) return <DashboardLayout><LoadingSpinner /></DashboardLayout>;

  return (
    <DashboardLayout>
      <div>
        {/* Header */}
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-gray-900 dark:text-gray-100">
            Patients Management
          </h1>
        </div>

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

        {/* Table */}
        <div className="bg-white dark:bg-gray-900 rounded-lg shadow-md overflow-hidden">
          <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
            <thead className="bg-gray-50 dark:bg-gray-800">
              <tr>
                {['ID', 'Name', 'Email', 'Phone', 'Gender', 'Address', 'Actions'].map((heading) => (
                  <th
                    key={heading}
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider 
                               dark:text-gray-300"
                  >
                    {heading}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody className="bg-white dark:bg-gray-900 divide-y divide-gray-200 dark:divide-gray-700">
              {patients.map((patient) => (
                <tr key={patient.id} className="hover:bg-gray-50 dark:hover:bg-gray-800">
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">
                    {patient.id}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 dark:text-gray-100">
                    {patient.name}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">
                    {patient.email}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">
                    {patient.phoneNumber || 'N/A'}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">
                    {patient.gender || 'N/A'}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">
                    {patient.address || 'N/A'}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium space-x-3">
                    <button
                      onClick={() => handleViewProfile(patient.id)}
                      className="text-blue-600 hover:text-blue-900 dark:text-blue-400 dark:hover:text-blue-300 inline-flex items-center"
                      title="View Profile"
                    >
                      <EyeIcon className="h-5 w-5" />
                    </button>
                    <button
                      onClick={() => handleDeletePatient(patient.id)}
                      className="text-red-600 hover:text-red-900 dark:text-red-400 dark:hover:text-red-300 inline-flex items-center"
                      title="Delete"
                    >
                      <TrashIcon className="h-5 w-5" />
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          {/* Empty State */}
          {patients.length === 0 && (
            <div className="text-center py-8 text-gray-500 dark:text-gray-400">
              No patients found
            </div>
          )}
        </div>
      </div>

      {/* View Patient Profile Modal */}
      <Modal
        isOpen={isProfileModalOpen}
        onClose={() => setIsProfileModalOpen(false)}
        title="Patient Profile"
      >
        {viewingPatient && (
          <div className="space-y-4">
            <div className="grid grid-cols-2 gap-4">
              <div>
                <p className="text-sm font-medium text-text-secondary dark:text-text-secondary-dark">ID</p>
                <p className="text-base text-text-primary dark:text-text-primary-dark">{viewingPatient.id}</p>
              </div>
              <div>
                <p className="text-sm font-medium text-text-secondary dark:text-text-secondary-dark">Name</p>
                <p className="text-base text-text-primary dark:text-text-primary-dark">{viewingPatient.name}</p>
              </div>
              <div>
                <p className="text-sm font-medium text-text-secondary dark:text-text-secondary-dark">Email</p>
                <p className="text-base text-text-primary dark:text-text-primary-dark">{viewingPatient.email}</p>
              </div>
              <div>
                <p className="text-sm font-medium text-text-secondary dark:text-text-secondary-dark">Phone Number</p>
                <p className="text-base text-text-primary dark:text-text-primary-dark">{viewingPatient.phoneNumber || 'N/A'}</p>
              </div>
              <div>
                <p className="text-sm font-medium text-text-secondary dark:text-text-secondary-dark">Gender</p>
                <p className="text-base text-text-primary dark:text-text-primary-dark">{viewingPatient.gender || 'N/A'}</p>
              </div>
              <div>
                <p className="text-sm font-medium text-text-secondary dark:text-text-secondary-dark">Date of Birth</p>
                <p className="text-base text-text-primary dark:text-text-primary-dark">{viewingPatient.dateOfBirth || 'N/A'}</p>
              </div>
              <div className="col-span-2">
                <p className="text-sm font-medium text-text-secondary dark:text-text-secondary-dark">Address</p>
                <p className="text-base text-text-primary dark:text-text-primary-dark">{viewingPatient.address || 'N/A'}</p>
              </div>
            </div>
            <div className="flex justify-end pt-4">
              <button
                onClick={() => setIsProfileModalOpen(false)}
                className="btn-primary"
              >
                Close
              </button>
            </div>
          </div>
        )}
      </Modal>
    </DashboardLayout>
  );
};

export default PatientsManagement;
