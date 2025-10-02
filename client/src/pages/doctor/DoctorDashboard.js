import React, { useState, useEffect } from 'react';
import DashboardLayout from '../../components/layout/DashboardLayout';
import { useAuth } from '../../contexts/AuthContext';
import { Link } from 'react-router-dom';
import { 
  CalendarIcon, 
  UserIcon,  
  ClockIcon,
  CheckCircleIcon,
  ChartBarIcon,
  BeakerIcon,
  HeartIcon
} from '@heroicons/react/24/outline';

const DoctorDashboard = () => {
  const { user } = useAuth();
  const [stats, setStats] = useState({
    todayAppointments: 0,
    totalPatients: 0,
    completedAppointments: 0,
    pendingAppointments: 0
  });

  useEffect(() => {
    // Simulated stats - in real app, fetch from API
    setStats({
      todayAppointments: 5,
      totalPatients: 127,
      completedAppointments: 89,
      pendingAppointments: 12
    });
  }, []);

  const recentAppointments = [
    { id: 1, patientName: 'John Smith', time: '09:00 AM', status: 'CONFIRMED' },
    { id: 2, patientName: 'Emma Wilson', time: '10:30 AM', status: 'PENDING' },
    { id: 3, patientName: 'Michael Brown', time: '02:00 PM', status: 'CONFIRMED' }
  ];

  const quickActions = [
    { icon: CalendarIcon, label: 'View Appointments', link: '/doctor/appointments', color: 'blue' },
    { icon: UserIcon, label: 'Update Profile', link: '/doctor/profile', color: 'green' },
    { icon: ChartBarIcon, label: 'View Reports', link: '/doctor/appointments', color: 'purple' }
  ];

  return (
    <DashboardLayout>
      <div className="space-y-6">
        {/* Header */}
        <div className="bg-gradient-to-r from-primary to-secondary rounded-xl shadow-lg p-6 sm:p-8">
          <h1 className="text-2xl sm:text-3xl font-bold text-white mb-2">
            Welcome back, {user?.name}!
          </h1>
          <p className="text-white/90">Here's your medical practice overview for today</p>
        </div>

        {/* Stats Cards */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 sm:gap-6">
          <div className="card p-6 bg-gradient-to-br from-blue-500 to-blue-600 text-white hover:shadow-xl transform hover:-translate-y-1 transition-all">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-blue-100 text-sm">Today's Appointments</p>
                <h3 className="text-3xl font-bold mt-2">{stats.todayAppointments}</h3>
              </div>
              <CalendarIcon className="h-12 w-12 text-blue-200" />
            </div>
          </div>

          <div className="card p-6 bg-gradient-to-br from-green-500 to-green-600 text-white hover:shadow-xl transform hover:-translate-y-1 transition-all">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-green-100 text-sm">Total Patients</p>
                <h3 className="text-3xl font-bold mt-2">{stats.totalPatients}</h3>
              </div>
              <UserIcon className="h-12 w-12 text-green-200" />
            </div>
          </div>

          <div className="card p-6 bg-gradient-to-br from-teal-500 to-teal-600 text-white hover:shadow-xl transform hover:-translate-y-1 transition-all">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-teal-100 text-sm">Completed</p>
                <h3 className="text-3xl font-bold mt-2">{stats.completedAppointments}</h3>
              </div>
              <CheckCircleIcon className="h-12 w-12 text-teal-200" />
            </div>
          </div>

          <div className="card p-6 bg-gradient-to-br from-yellow-500 to-yellow-600 text-white hover:shadow-xl transform hover:-translate-y-1 transition-all">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-yellow-100 text-sm">Pending</p>
                <h3 className="text-3xl font-bold mt-2">{stats.pendingAppointments}</h3>
              </div>
              <ClockIcon className="h-12 w-12 text-yellow-200" />
            </div>
          </div>
        </div>

        {/* Main Content Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Today's Schedule */}
          <div className="lg:col-span-2 card p-6">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-xl font-semibold text-gray-900 dark:text-white">Today's Schedule</h2>
              <Link to="/doctor/appointments" className="text-primary hover:text-primary-hover text-sm font-medium">
                View All →
              </Link>
            </div>
            <div className="space-y-4">
              {recentAppointments.map((appointment) => (
                <div key={appointment.id} className="flex items-center justify-between p-4 bg-gradient-to-r from-primary/5 to-secondary/5 rounded-lg border border-border hover:shadow-md transition-shadow">
                  <div className="flex items-center space-x-4">
                    <div className="w-10 h-10 bg-gradient-to-br from-primary to-secondary rounded-full flex items-center justify-center text-white font-semibold">
                      {appointment.patientName.split(' ').map(n => n[0]).join('')}
                    </div>
                    <div>
                      <p className="font-medium text-gray-900 dark:text-white">{appointment.patientName}</p>
                      <p className="text-sm text-gray-600 dark:text-gray-400 flex items-center">
                        <ClockIcon className="h-4 w-4 mr-1" />
                        {appointment.time}
                      </p>
                    </div>
                  </div>
                  <span className={`px-3 py-1 rounded-full text-xs font-medium ${
                    appointment.status === 'CONFIRMED' 
                      ? 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200' 
                      : 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200'
                  }`}>
                    {appointment.status}
                  </span>
                </div>
              ))}
            </div>
          </div>

          {/* Quick Actions */}
          <div className="card p-6">
            <h2 className="text-xl font-semibold text-gray-900 dark:text-white mb-6">Quick Actions</h2>
            <div className="space-y-3">
              {quickActions.map((action, index) => (
                <Link
                  key={index}
                  to={action.link}
                  className={`flex items-center p-4 rounded-lg bg-gradient-to-r from-${action.color}-500/10 to-${action.color}-600/10 border border-${action.color}-200 dark:border-${action.color}-800 hover:shadow-md hover:scale-105 transform transition-all`}
                >
                  <action.icon className={`h-6 w-6 text-${action.color}-600 dark:text-${action.color}-400 mr-3`} />
                  <span className="font-medium text-gray-900 dark:text-white">{action.label}</span>
                </Link>
              ))}
            </div>
          </div>
        </div>

        {/* Additional Info Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {/* Specialization Card */}
          <div className="card p-6 bg-gradient-to-br from-primary/5 to-secondary/5">
            <div className="flex items-center mb-4">
              <BeakerIcon className="h-8 w-8 text-primary mr-3" />
              <h3 className="text-lg font-semibold text-gray-900 dark:text-white">Specialization</h3>
            </div>
            <p className="text-gray-600 dark:text-gray-400">Cardiology</p>
            <p className="text-sm text-gray-500 dark:text-gray-500 mt-2">15 years of experience</p>
          </div>

          {/* Patient Satisfaction */}
          <div className="card p-6 bg-gradient-to-br from-success/5 to-success/10">
            <div className="flex items-center mb-4">
              <HeartIcon className="h-8 w-8 text-success mr-3" />
              <h3 className="text-lg font-semibold text-gray-900 dark:text-white">Patient Satisfaction</h3>
            </div>
            <div className="flex items-baseline">
              <span className="text-3xl font-bold text-success">98%</span>
              <span className="text-sm text-gray-600 dark:text-gray-400 ml-2">Positive feedback</span>
            </div>
          </div>

          {/* Availability Status */}
          <div className="card p-6 bg-gradient-to-br from-warning/5 to-warning/10">
            <div className="flex items-center mb-4">
              <ClockIcon className="h-8 w-8 text-warning mr-3" />
              <h3 className="text-lg font-semibold text-gray-900 dark:text-white">Availability</h3>
            </div>
            <div className="flex items-center">
              <div className="h-3 w-3 bg-success rounded-full mr-2 animate-pulse"></div>
              <span className="text-gray-600 dark:text-gray-400">Available</span>
            </div>
            <p className="text-sm text-gray-500 dark:text-gray-500 mt-2">Mon-Fri: 9AM - 5PM</p>
          </div>
        </div>
      </div>
    </DashboardLayout>
  );
};

export default DoctorDashboard;
