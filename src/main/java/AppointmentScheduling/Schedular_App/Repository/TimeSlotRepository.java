package AppointmentScheduling.Schedular_App.Repository;

import AppointmentScheduling.Schedular_App.Entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByScheduleProviderIdAndIsBookedFalse(Long scheduleProviderId);
    List<TimeSlot> findByScheduleProviderIdAndStartTimeAfter(Long scheduleProviderId, LocalDateTime dateTime);
    TimeSlot findByIdAndIsBookedFalse(Long id);
}
