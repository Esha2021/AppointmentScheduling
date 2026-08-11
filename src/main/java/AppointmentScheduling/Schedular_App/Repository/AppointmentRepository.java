package AppointmentScheduling.Schedular_App.Repository;

import AppointmentScheduling.Schedular_App.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
   List<Appointment> findByUserId(Long userId);
    List<Appointment> findByScheduleProviderId(Long scheduleProviderId);
    //boolean existsByScheduleProviderIdAndDatetime(Long scheduleProviderId, LocalDateTime datetime);

    boolean existsByDatetime(LocalDateTime dateTime);
    long countByScheduleProviderId(Long scheduleProviderId);
}
