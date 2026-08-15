import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import BookAppointment from '../pages/BookAppointment';

// Mock the API
jest.mock('../services/api', () => ({
  appointmentAPI: {
    bookAppointment: jest.fn(() => Promise.resolve({ data: { id: 1 } })),
  },
  providerAPI: {
    getAllProviders: jest.fn(() =>
      Promise.resolve({
        data: [
          { id: 1, scheduleProviderName: 'Dr. Smith', emailId: 'dr@hospital.com' },
          { id: 2, scheduleProviderName: 'Dr. Jones', emailId: 'jones@hospital.com' },
        ],
      })
    ),
  },
  timeSlotAPI: {
    getAvailableSlots: jest.fn(() =>
      Promise.resolve({
        data: [
          { id: 1, startTime: '2026-08-20T09:00:00', endTime: '2026-08-20T09:30:00' },
          { id: 2, startTime: '2026-08-20T10:00:00', endTime: '2026-08-20T10:30:00' },
        ],
      })
    ),
  },
}));

// Mock localStorage
global.localStorage = {
  getItem: jest.fn((key) => {
    if (key === 'username') return 'testuser';
    if (key === 'userId') return '1';
    return null;
  }),
  setItem: jest.fn(),
};

const renderBookAppointment = () => {
  return render(
    <BrowserRouter>
      <BookAppointment />
    </BrowserRouter>
  );
};

describe('BookAppointment Component', () => {
  test('renders booking form with all fields', async () => {
    renderBookAppointment();

    await waitFor(() => {
      expect(screen.getByLabelText(/appointment name/i)).toBeInTheDocument();
      expect(screen.getByLabelText(/email address/i)).toBeInTheDocument();
      expect(screen.getByLabelText(/location/i)).toBeInTheDocument();
    });
  });

  test('loads providers on component mount', async () => {
    renderBookAppointment();

    await waitFor(() => {
      expect(screen.getByText(/dr. smith/i)).toBeInTheDocument();
      expect(screen.getByText(/dr. jones/i)).toBeInTheDocument();
    });
  });

  test('displays book appointment button', async () => {
    renderBookAppointment();

    await waitFor(() => {
      expect(screen.getByRole('button', { name: /book appointment/i })).toBeInTheDocument();
    });
  });

  test('validates email format', async () => {
    renderBookAppointment();

    const emailInput = await screen.findByLabelText(/email address/i);
    fireEvent.change(emailInput, { target: { value: 'invalidemail' } });
    fireEvent.click(screen.getByRole('button', { name: /book appointment/i }));

    await waitFor(() => {
      expect(screen.getByText(/valid email/i)).toBeInTheDocument();
    });
  });

  test('shows info section with tips', async () => {
    renderBookAppointment();

    await waitFor(() => {
      expect(screen.getByText(/appointment details/i)).toBeInTheDocument();
      expect(screen.getByText(/tips/i)).toBeInTheDocument();
    });
  });
});
