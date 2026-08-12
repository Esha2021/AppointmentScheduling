package AppointmentScheduling.Schedular_App.Repository;

import AppointmentScheduling.Schedular_App.Entity.Appointment;
import AppointmentScheduling.Schedular_App.Entity.ScheduleProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ScheduleProviderRepository extends JpaRepository<ScheduleProvider, Long> {
    boolean existsByDatetime( LocalDateTime datetime);

    Optional<ScheduleProvider> findByScheduleProviderName(String scheduleProviderName);
}
