import api from './api';

export const doctorService = {
  getAllDoctors: async () => {
    return await api.get('/api/doctors');
  },

  getDoctorById: async (id) => {
    return await api.get(`/api/doctors/${id}`);
  },

  getDoctorsBySpecialization: async (specialization) => {
    return await api.get(`/api/doctors/specialization/${specialization}`);
  },

  updateDoctor: async (id, doctorData) => {
    return await api.put(`/api/doctors/${id}`, doctorData);
  },

  deleteDoctor: async (id) => {
    return await api.delete(`/api/doctors/${id}`);
  },
};
