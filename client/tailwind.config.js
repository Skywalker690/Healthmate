/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: 'hsl(210, 85%, 45%)',
          hover: 'hsl(210, 85%, 38%)',
          dark: 'hsl(210, 80%, 55%)',
          'dark-hover': 'hsl(210, 80%, 62%)',
        },
        secondary: {
          DEFAULT: 'hsl(195, 75%, 50%)',
          dark: 'hsl(195, 65%, 55%)',
        },
        success: {
          DEFAULT: 'hsl(142, 70%, 45%)',
          dark: 'hsl(142, 60%, 50%)',
        },
        warning: {
          DEFAULT: 'hsl(38, 92%, 50%)',
          dark: 'hsl(38, 85%, 55%)',
        },
        error: {
          DEFAULT: 'hsl(0, 70%, 50%)',
          dark: 'hsl(0, 65%, 55%)',
        },
        background: {
          DEFAULT: 'hsl(210, 20%, 98%)',
          dark: 'hsl(215, 25%, 8%)',
        },
        surface: {
          DEFAULT: 'hsl(0, 0%, 100%)',
          dark: 'hsl(215, 20%, 12%)',
        },
        text: {
          primary: 'hsl(215, 25%, 15%)',
          secondary: 'hsl(215, 15%, 45%)',
          'primary-dark': 'hsl(210, 15%, 92%)',
          'secondary-dark': 'hsl(210, 10%, 70%)',
        },
        border: {
          DEFAULT: 'hsl(215, 20%, 88%)',
          dark: 'hsl(215, 15%, 22%)',
        },
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', '-apple-system', 'sans-serif'],
      },
      spacing: {
        '18': '4.5rem',
      },
    },
  },
  plugins: [],
}
