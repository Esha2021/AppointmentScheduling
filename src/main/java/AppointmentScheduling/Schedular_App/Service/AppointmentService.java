package AppointmentScheduling.Schedular_App.Service;

import AppointmentScheduling.Schedular_App.Dto.AppointmentDTO;
import AppointmentScheduling.Schedular_App.Entity.Appointment;
import AppointmentScheduling.Schedular_App.Entity.ScheduleProvider;
import AppointmentScheduling.Schedular_App.Entity.Status;
import AppointmentScheduling.Schedular_App.Entity.User;
import AppointmentScheduling.Schedular_App.Repository.AppointmentRepository;
import AppointmentScheduling.Schedular_App.Repository.ScheduleProviderRepository;
import AppointmentScheduling.Schedular_App.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleProviderRepository scheduleProviderRepository;

    public AppointmentDTO bookAppointment(Long userId, Long scheduleproviderId, String appointeeName,String appointeeEmailId,LocalDateTime datetime, String location) {


        if (appointmentRepository.existsByDatetime(datetime) || scheduleProviderRepository.existsByDatetime(datetime)) {

                throw new RuntimeException("Time slot not available. Please choose another time.");
                    }

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ScheduleProvider provider = scheduleProviderRepository.findById(scheduleproviderId).orElseThrow(() -> new RuntimeException("Provider not found"));
        //ScheduleProvider providerName = scheduleProviderRepository.findByScheduleProviderName(scheduleProviderName).orElseThrow(() -> new RuntimeException("Provider not found"));
        System.out.println("Provider Name: " + provider.getScheduleProviderName());


        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setScheduleProvider(provider);
        appointment.setAppointeeName(appointeeName);
        appointment.setAppointee_emailId(appointeeEmailId);
        appointment.setDatetime(datetime);
        appointment.setStatus(Status.confirmed);
        appointment.setLocation(location);

        appointmentRepository.save(appointment);
        return new AppointmentDTO(appointment);
    }
    }