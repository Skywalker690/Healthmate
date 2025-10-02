import api from './api';

export const userService = {
  getAllUsers: async () => {
    return await api.get('/api/users');
  },

  getUserById: async (id) => {
    return await api.get(`/api/users/${id}`);
  },

  getCurrentUser: async () => {
    return await api.get('/api/users/me');
  },

  updateUser: async (id, userData) => {
    return await api.put(`/api/users/${id}`, userData);
  },

  updateCurrentUser: async (userData) => {
    return await api.put('/api/users/me', userData);
  },

  deleteUser: async (id) => {
    return await api.delete(`/api/users/${id}`);
  },

  getUsersByRole: async (role) => {
    return await api.get(`/api/users/role/${role}`);
  },

  changePassword: async (oldPassword, newPassword) => {
    return await api.put('/api/users/me/password', { oldPassword, newPassword });
  },
};
