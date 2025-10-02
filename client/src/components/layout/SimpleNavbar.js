// SimpleNavbar.jsx
import React from 'react';
import { Link } from 'react-router-dom';
import { SunIcon, MoonIcon } from '@heroicons/react/24/outline';

const SimpleNavbar = ({ theme, toggleTheme }) => {
  return (
    <nav className="bg-surface dark:bg-surface-dark py-4 px-6 flex justify-between items-center shadow-md sticky top-0 z-40">
      {/* Name only */}
      <span className="font-bold text-lg text-gray-900 dark:text-white">
        HealthCare
      </span>

      {/* Right side: Links + Theme Toggle */}
      <div className="flex items-center gap-4">
        <Link
          to="/register"
          className="px-4 py-2 rounded-md text-gray-900 dark:text-white hover:bg-primary/10 dark:hover:bg-primary-dark/20 transition-colors text-sm sm:text-base"
        >
          Register
        </Link>
        <Link
          to="/login"
          className="px-4 py-2 rounded-md text-gray-900 dark:text-white hover:bg-primary/10 dark:hover:bg-primary-dark/20 transition-colors text-sm sm:text-base"
        >
          Login
        </Link>

        {/* Theme Toggle */}
        <button
          onClick={toggleTheme}
          className="p-2 rounded-full bg-gray-200 dark:bg-gray-700 hover:bg-gray-300 dark:hover:bg-gray-600 transition-colors flex items-center justify-center"
          aria-label="Toggle theme"
        >
          {theme === 'dark' ? (
            <SunIcon className="h-5 w-5 text-yellow-400" />
          ) : (
            <MoonIcon className="h-5 w-5 text-gray-900" />
          )}
        </button>
      </div>
    </nav>
  );
};

export default SimpleNavbar;
