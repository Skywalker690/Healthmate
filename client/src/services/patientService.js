import api from './api';

export const patientService = {
  getAllPatients: async () => {
    return await api.get('/api/patients');
  },

  getPatientById: async (id) => {
    return await api.get(`/api/patients/${id}`);
  },

  updatePatient: async (id, patientData) => {
    return await api.put(`/api/patients/${id}`, patientData);
  },

  deletePatient: async (id) => {
    return await api.delete(`/api/patients/${id}`);
  },
};
