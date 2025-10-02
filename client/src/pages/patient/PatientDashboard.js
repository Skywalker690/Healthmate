import React, { useState, useEffect } from 'react';
import DashboardLayout from '../../components/layout/DashboardLayout';
import { useAuth } from '../../contexts/AuthContext';
import { Link } from 'react-router-dom';
import {
  CalendarIcon,
  UserIcon,
  HeartIcon,
  ClockIcon,
  CheckCircleIcon,
  PlusCircleIcon,
  DocumentTextIcon,
  PhoneIcon
} from '@heroicons/react/24/outline';

const PatientDashboard = () => {
  const { user } = useAuth();
  const [stats, setStats] = useState({
    upcomingAppointments: 0,
    completedAppointments: 0,
    prescriptions: 0,
    testReports: 0
  });

  useEffect(() => {
    // Simulated stats - in real app, fetch from API
    setStats({
      upcomingAppointments: 3,
      completedAppointments: 12,
      prescriptions: 5,
      testReports: 8
    });
  }, []);

  const upcomingAppointments = [
    { id: 1, doctorName: 'Dr. Sarah Johnson', specialty: 'Cardiologist', date: '2024-02-15', time: '10:00 AM', status: 'CONFIRMED' },
    { id: 2, doctorName: 'Dr. Michael Chen', specialty: 'Dermatologist', date: '2024-02-18', time: '02:30 PM', status: 'PENDING' }
  ];

  const healthTips = [
    { icon: HeartIcon, tip: 'Stay hydrated - drink at least 8 glasses of water daily', color: 'blue' },
    { icon: ClockIcon, tip: 'Get 7-8 hours of quality sleep each night', color: 'purple' },
    { icon: HeartIcon, tip: 'Exercise for at least 30 minutes daily', color: 'green' }
  ];

  return (
    <DashboardLayout>
      <div className="space-y-6">
        {/* Header */}
        <div className="bg-gradient-to-r from-primary to-secondary rounded-xl shadow-lg p-6 sm:p-8">
          <h1 className="text-2xl sm:text-3xl font-bold text-white mb-2">
            Welcome back, {user?.name}!
          </h1>
          <p className="text-white/90">Your health dashboard - manage appointments and track your wellness journey</p>
        </div>

        {/* Stats Cards */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 sm:gap-6">
          <div className="card p-6 bg-gradient-to-br from-blue-500 to-blue-600 text-white hover:shadow-xl transform hover:-translate-y-1 transition-all">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-blue-100 text-sm">Upcoming</p>
                <h3 className="text-3xl font-bold mt-2">{stats.upcomingAppointments}</h3>
              </div>
              <CalendarIcon className="h-12 w-12 text-blue-200" />
            </div>
            <p className="text-blue-100 text-xs mt-2">Appointments scheduled</p>
          </div>

          <div className="card p-6 bg-gradient-to-br from-green-500 to-green-600 text-white hover:shadow-xl transform hover:-translate-y-1 transition-all">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-green-100 text-sm">Completed</p>
                <h3 className="text-3xl font-bold mt-2">{stats.completedAppointments}</h3>
              </div>
              <CheckCircleIcon className="h-12 w-12 text-green-200" />
            </div>
            <p className="text-green-100 text-xs mt-2">Total consultations</p>
          </div>

          <div className="card p-6 bg-gradient-to-br from-purple-500 to-purple-600 text-white hover:shadow-xl transform hover:-translate-y-1 transition-all">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-purple-100 text-sm">Prescriptions</p>
                <h3 className="text-3xl font-bold mt-2">{stats.prescriptions}</h3>
              </div>
              <DocumentTextIcon className="h-12 w-12 text-purple-200" />
            </div>
            <p className="text-purple-100 text-xs mt-2">Active medications</p>
          </div>

          <div className="card p-6 bg-gradient-to-br from-teal-500 to-teal-600 text-white hover:shadow-xl transform hover:-translate-y-1 transition-all">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-teal-100 text-sm">Reports</p>
                <h3 className="text-3xl font-bold mt-2">{stats.testReports}</h3>
              </div>
              <DocumentTextIcon className="h-12 w-12 text-teal-200" />
            </div>
            <p className="text-teal-100 text-xs mt-2">Medical reports</p>
          </div>
        </div>

        {/* Main Content Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Upcoming Appointments */}
          <div className="lg:col-span-2 card p-6">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-xl font-semibold text-gray-900 dark:text-white">Upcoming Appointments</h2>
              <Link to="/patient/appointments" className="text-primary hover:text-primary-hover text-sm font-medium">
                View All →
              </Link>
            </div>
            {upcomingAppointments.length > 0 ? (
              <div className="space-y-4">
                {upcomingAppointments.map((appointment) => (
                  <div key={appointment.id} className="p-4 bg-gradient-to-r from-primary/5 to-secondary/5 rounded-lg border border-border hover:shadow-md transition-shadow">
                    <div className="flex items-start justify-between">
                      <div className="flex items-start space-x-4">
                        <div className="w-12 h-12 bg-gradient-to-br from-primary to-secondary rounded-full flex items-center justify-center text-white font-semibold flex-shrink-0">
                          {appointment.doctorName.split(' ').map(n => n[0]).join('')}
                        </div>
                        <div>
                          <p className="font-semibold text-gray-900 dark:text-white">{appointment.doctorName}</p>
                          <p className="text-sm text-gray-600 dark:text-gray-400">{appointment.specialty}</p>
                          <div className="flex items-center mt-2 text-sm text-gray-600 dark:text-gray-400">
                            <CalendarIcon className="h-4 w-4 mr-1" />
                            <span className="mr-3">{appointment.date}</span>
                            <ClockIcon className="h-4 w-4 mr-1" />
                            <span>{appointment.time}</span>
                          </div>
                        </div>
                      </div>
                      <span className={`px-3 py-1 rounded-full text-xs font-medium whitespace-nowrap ${
                        appointment.status === 'CONFIRMED'
                          ? 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200'
                          : 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200'
                      }`}>
                        {appointment.status}
                      </span>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <div className="text-center py-8">
                <CalendarIcon className="h-16 w-16 text-gray-400 mx-auto mb-4" />
                <p className="text-gray-600 dark:text-gray-400 mb-4">No upcoming appointments</p>
                <Link
                  to="/patient/book-appointment"
                  className="btn-primary inline-flex items-center"
                >
                  <PlusCircleIcon className="h-5 w-5 mr-2" />
                  Book Appointment
                </Link>
              </div>
            )}
          </div>

          {/* Quick Actions */}
          <div className="card p-6">
            <h2 className="text-xl font-semibold text-gray-900 dark:text-white mb-6">Quick Actions</h2>
            <div className="space-y-3">
              <Link
                to="/patient/book-appointment"
                className="flex items-center p-4 rounded-lg bg-gradient-to-r from-blue-500/10 to-blue-600/10 border border-blue-200 dark:border-blue-800 hover:shadow-md hover:scale-105 transform transition-all"
              >
                <PlusCircleIcon className="h-6 w-6 text-blue-600 dark:text-blue-400 mr-3" />
                <span className="font-medium text-gray-900 dark:text-white">Book Appointment</span>
              </Link>

              <Link
                to="/patient/appointments"
                className="flex items-center p-4 rounded-lg bg-gradient-to-r from-green-500/10 to-green-600/10 border border-green-200 dark:border-green-800 hover:shadow-md hover:scale-105 transform transition-all"
              >
                <CalendarIcon className="h-6 w-6 text-green-600 dark:text-green-400 mr-3" />
                <span className="font-medium text-gray-900 dark:text-white">My Appointments</span>
              </Link>

              <Link
                to="/patient/profile"
                className="flex items-center p-4 rounded-lg bg-gradient-to-r from-purple-500/10 to-purple-600/10 border border-purple-200 dark:border-purple-800 hover:shadow-md hover:scale-105 transform transition-all"
              >
                <UserIcon className="h-6 w-6 text-purple-600 dark:text-purple-400 mr-3" />
                <span className="font-medium text-gray-900 dark:text-white">Update Profile</span>
              </Link>
            </div>
          </div>
        </div>

        {/* Health Tips & Emergency Contact */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {/* Health Tips */}
          <div className="card p-6 bg-gradient-to-br from-success/5 to-success/10">
            <h2 className="text-lg font-semibold text-gray-900 dark:text-white mb-4 flex items-center">
              <HeartIcon className="h-6 w-6 text-success mr-2" />
              Health Tips
            </h2>
            <div className="space-y-3">
              {healthTips.map((tip, index) => (
                <div key={index} className="flex items-start space-x-3">
                  <tip.icon className={`h-5 w-5 text-${tip.color}-600 dark:text-${tip.color}-400 flex-shrink-0 mt-0.5`} />
                  <p className="text-sm text-gray-600 dark:text-gray-400">{tip.tip}</p>
                </div>
              ))}
            </div>
          </div>

          {/* Emergency Contact */}
          <div className="card p-6 bg-gradient-to-br from-error/5 to-error/10">
            <h2 className="text-lg font-semibold text-gray-900 dark:text-white mb-4 flex items-center">
              <PhoneIcon className="h-6 w-6 text-error mr-2" />
              Emergency Contact
            </h2>
            <div className="space-y-3">
              <div className="flex items-center p-3 bg-white dark:bg-gray-800 rounded-lg">
                <div className="flex-1">
                  <p className="text-sm text-gray-600 dark:text-gray-400">Emergency Hotline</p>
                  <p className="text-lg font-semibold text-error">1-800-EMERGENCY</p>
                </div>
              </div>
              <div className="flex items-center p-3 bg-white dark:bg-gray-800 rounded-lg">
                <div className="flex-1">
                  <p className="text-sm text-gray-600 dark:text-gray-400">General Inquiry</p>
                  <p className="text-lg font-semibold text-primary">1-800-HEALTH</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </DashboardLayout>
  );
};

export default PatientDashboard;
