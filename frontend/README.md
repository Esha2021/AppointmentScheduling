# Appointment Scheduling Frontend

A modern React application for managing user authentication and appointment scheduling.

## Features

- **User Authentication**
  - User registration with password validation
  - User login with JWT token management
  - Persistent session using localStorage

- **Appointment Management**
  - View all appointments in a clean dashboard
  - Book new appointments with date/time selection
  - Filter and organize appointments by status
  - Responsive design for mobile and desktop

## Prerequisites

- Node.js (v14 or higher)
- npm or yarn
- Backend API running on `http://localhost:8080`

## Installation

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

## Running the Application

Start the development server:
```bash
npm start
```

The app will open at `http://localhost:3000`

## Project Structure

```
frontend/
├── public/
│   └── index.html
├── src/
│   ├── components/
│   │   ├── Navbar.js          # Navigation bar component
│   │   └── Navbar.css
│   ├── pages/
│   │   ├── Login.js           # Login page
│   │   ├── Register.js        # Registration page
│   │   ├── Dashboard.js       # Appointments dashboard
│   │   ├── BookAppointment.js # Booking form
│   │   ├── Auth.css           # Auth pages styles
│   │   ├── Dashboard.css
│   │   └── BookAppointment.css
│   ├── services/
│   │   └── api.js             # API client configuration
│   ├── App.js                 # Main app component with routing
│   ├── App.css
│   ├── index.js               # Entry point
│   └── index.css              # Global styles
└── package.json
```

## API Endpoints

The app expects the following backend endpoints:

### Authentication
- `POST /auth/login` - User login
- `POST /auth/register` - User registration

### Appointments
- `GET /appointment/all` - Get all appointments
- `POST /appointment/book` - Book a new appointment

## Environment Variables

The API base URL is configured in `src/services/api.js`:
```javascript
const API_BASE_URL = 'http://localhost:8080';
```

To change the backend URL, update this constant.

## Authentication Flow

1. User registers or logs in
2. JWT token is received from backend
3. Token is stored in localStorage
4. Token is automatically attached to API requests via Axios interceptor
5. User is redirected to dashboard on successful login

## Building for Production

```bash
npm run build
```

This creates an optimized production build in the `build/` folder.

## Features Overview

### Login Page
- Username and password fields
- Error handling and feedback
- Link to registration page
- Responsive design with gradient background

### Registration Page
- Username, password, and confirm password fields
- Password validation (minimum 6 characters)
- Duplicate username detection
- Success message with auto-redirect to login

### Dashboard
- View all appointments with details
- Status badges (Confirmed, Pending, Cancelled, Completed)
- Quick link to book new appointments
- Responsive grid layout
- Empty state message with action button

### Book Appointment Page
- Form for entering appointment details:
  - Appointment name
  - Email address
  - Date and time picker
  - Location
- Email validation
- Helpful tips section
- Back to dashboard button

## Styling

The app uses vanilla CSS with:
- Gradient backgrounds and modern colors
- Responsive flexbox and grid layouts
- Smooth transitions and hover effects
- Mobile-first design approach
- Accessible color contrasts

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)
