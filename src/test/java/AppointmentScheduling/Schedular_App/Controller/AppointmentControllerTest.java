package AppointmentScheduling.Schedular_App.Controller;

import AppointmentScheduling.Schedular_App.Dto.AppointmentDTO;
import AppointmentScheduling.Schedular_App.Entity.Appointment;
import AppointmentScheduling.Schedular_App.Entity.ScheduleProvider;
import AppointmentScheduling.Schedular_App.Entity.Status;
import AppointmentScheduling.Schedular_App.Entity.User;
import AppointmentScheduling.Schedular_App.Repository.AppointmentRepository;
import AppointmentScheduling.Schedular_App.Service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class AppointmentControllerTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAppointments() {
        User user = new User(1L, "testuser", "password", new java.util.HashSet<>());
        ScheduleProvider provider = new ScheduleProvider(1L, "Dr. Smith", "dr@hospital.com", null, "Cardiologist");

        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setAppointeeName("John Doe");
        appointment.setAppointee_emailId("john@example.com");
        appointment.setDatetime(LocalDateTime.now());
        appointment.setLocation("Hospital");
        appointment.setStatus(Status.confirmed);
        appointment.setUser(user);
        appointment.setScheduleProvider(provider);

        when(appointmentRepository.findAll()).thenReturn(Arrays.asList(appointment));

        List<Appointment> appointments = appointmentRepository.findAll();

        assertNotNull(appointments);
        assertEquals(1, appointments.size());
        assertEquals("John Doe", appointments.get(0).getAppointeeName());
    }

    @Test
    void testBookAppointment() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);

        // Create mock Appointment object
        User user = new User(1L, "testuser", "password", new HashSet<>());
        ScheduleProvider provider = new ScheduleProvider(1L, "Dr. Smith", "dr@hospital.com", null, "Cardiologist");

        Appointment mockAppointment = new Appointment();
        mockAppointment.setId(1L);
        mockAppointment.setAppointeeName("Jane Doe");
        mockAppointment.setAppointee_emailId("jane@example.com");
        mockAppointment.setDatetime(dateTime);
        mockAppointment.setLocation("Hospital Room 101");
        mockAppointment.setStatus(Status.confirmed);
        mockAppointment.setUser(user);
        mockAppointment.setScheduleProvider(provider);

        AppointmentDTO mockDTO = new AppointmentDTO(mockAppointment);

        when(appointmentService.bookAppointment(
            anyLong(), anyLong(), anyString(), anyString(), any(LocalDateTime.class), anyString()
        )).thenReturn(mockDTO);

        ResponseEntity<AppointmentDTO> response = appointmentController.bookAppointment(
            1L, 1L, "Jane Doe", "jane@example.com", dateTime, "Hospital Room 101"
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Jane Doe", response.getBody().getAppointeeName());
    }
}
