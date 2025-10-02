import React, { createContext, useState, useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/authService';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [role, setRole] = useState(localStorage.getItem('role'));
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    // Check if token exists and fetch current user
    const initializeAuth = async () => {
      const storedToken = localStorage.getItem('token');
      const storedRole = localStorage.getItem('role');
      
      if (storedToken) {
        try {
          const response = await authService.getCurrentUser();
          if (response.statusCode === 200) {
            setUser(response.user);
            setToken(storedToken);
            setRole(storedRole);
          } else {
            // Token is invalid, clear it
            localStorage.removeItem('token');
            localStorage.removeItem('role');
            setUser(null);
            setToken(null);
            setRole(null);
          }
        } catch (error) {
          console.error('Failed to fetch current user:', error);
          localStorage.removeItem('token');
          localStorage.removeItem('role');
          setUser(null);
          setToken(null);
          setRole(null);
        }
      }
      setLoading(false);
    };

    initializeAuth();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const login = async (email, password) => {
    try {
      const response = await authService.login(email, password);
      if (response.statusCode === 200) {
        const { token, role } = response;
        localStorage.setItem('token', token);
        localStorage.setItem('role', role);
        setToken(token);
        setRole(role);
        
        // Fetch user details
        const userResponse = await authService.getCurrentUser();
        if (userResponse.statusCode === 200) {
          setUser(userResponse.user);
        }
        
        // Navigate based on role
        if (role === 'ROLE_ADMIN') {
          navigate('/admin');
        } else if (role === 'ROLE_DOCTOR') {
          navigate('/doctor');
        } else if (role === 'ROLE_PATIENT') {
          navigate('/patient');
        }
        
        return { success: true };
      } else {
        return { success: false, message: response.message || 'Login failed' };
      }
    } catch (error) {
      console.error('Login error:', error);
      return { success: false, message: error.response?.data?.message || 'Login failed' };
    }
  };

  const register = async (userData) => {
    try {
      const response = await authService.register(userData);
      if (response.statusCode === 200) {
        return { success: true, message: response.message };
      } else {
        return { success: false, message: response.message || 'Registration failed' };
      }
    } catch (error) {
      console.error('Register error:', error);
      return { success: false, message: error.response?.data?.message || 'Registration failed' };
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    setUser(null);
    setToken(null);
    setRole(null);
    navigate('/login');
  };

  const value = {
    user,
    token,
    role,
    loading,
    login,
    register,
    logout,
    isAuthenticated: !!token,
    isAdmin: role === 'ROLE_ADMIN',
    isDoctor: role === 'ROLE_DOCTOR',
    isPatient: role === 'ROLE_PATIENT',
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
