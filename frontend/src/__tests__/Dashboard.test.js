import { render, screen, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import Dashboard from '../pages/Dashboard';

// Mock the API
jest.mock('../services/api', () => ({
  default: {
    get: jest.fn(),
  },
  appointmentAPI: {
    getAllAppointments: jest.fn(() =>
      Promise.resolve({
        data: [
          {
            id: 1,
            appointeeName: 'John Doe',
            appointeeEmailId: 'john@example.com',
            datetime: '2026-08-20T10:00:00',
            location: 'Hospital Room 101',
            status: 'confirmed',
            userName: 'testuser',
            providerName: 'Dr. Smith',
          },
        ],
      })
    ),
  },
}));

// Mock localStorage
const localStorageMock = {
  getItem: jest.fn((key) => {
    if (key === 'username') return 'testuser';
    return null;
  }),
  setItem: jest.fn(),
  removeItem: jest.fn(),
};
global.localStorage = localStorageMock;

const renderDashboard = () => {
  return render(
    <BrowserRouter>
      <Dashboard />
    </BrowserRouter>
  );
};

describe('Dashboard Component', () => {
  test('renders dashboard with heading', async () => {
    renderDashboard();

    await waitFor(() => {
      expect(screen.getByText(/appointment dashboard/i)).toBeInTheDocument();
    });
  });

  test('displays book appointment button', async () => {
    renderDashboard();

    await waitFor(() => {
      expect(screen.getByRole('button', { name: /book new appointment/i })).toBeInTheDocument();
    });
  });

  test('loads and displays appointments', async () => {
    renderDashboard();

    await waitFor(() => {
      expect(screen.getByText(/john doe/i)).toBeInTheDocument();
      expect(screen.getByText(/john@example.com/i)).toBeInTheDocument();
      expect(screen.getByText(/dr. smith/i)).toBeInTheDocument();
    });
  });

  test('shows appointment status badge', async () => {
    renderDashboard();

    await waitFor(() => {
      expect(screen.getByText(/confirmed/i)).toBeInTheDocument();
    });
  });

  test('displays appointment details in cards', async () => {
    renderDashboard();

    await waitFor(() => {
      expect(screen.getByText(/hospital room 101/i)).toBeInTheDocument();
    });
  });
});
