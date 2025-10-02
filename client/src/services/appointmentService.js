import api from './api';

export const appointmentService = {
  getAllAppointments: async () => {
    return await api.get('/api/appointments');
  },

  getAppointmentById: async (id) => {
    return await api.get(`/api/appointments/${id}`);
  },

  getAppointmentByCode: async (code) => {
    return await api.get(`/api/appointments/code/${code}`);
  },

  getAppointmentsByDoctor: async (doctorId) => {
    return await api.get(`/api/appointments/doctor/${doctorId}`);
  },

  getAppointmentsByPatient: async (patientId) => {
    return await api.get(`/api/appointments/patient/${patientId}`);
  },

  createAppointment: async (patientId, doctorId, appointmentData) => {
    return await api.post(`/api/appointments/${patientId}/${doctorId}`, appointmentData);
  },

  updateAppointmentStatus: async (id, status) => {
    return await api.put(`/api/appointments/${id}/status`, { status });
  },

  deleteAppointment: async (id) => {
    return await api.delete(`/api/appointments/${id}`);
  },
};
