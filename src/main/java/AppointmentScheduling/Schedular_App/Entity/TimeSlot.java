package AppointmentScheduling.Schedular_App.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TimeSlot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_provider_id", nullable = false)
    private ScheduleProvider scheduleProvider;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isBooked = false;

    public TimeSlot() {
    }

    public TimeSlot(ScheduleProvider scheduleProvider, LocalDateTime startTime, LocalDateTime endTime) {
        this.scheduleProvider = scheduleProvider;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isBooked = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScheduleProvider getScheduleProvider() {
        return scheduleProvider;
    }

    public void setScheduleProvider(ScheduleProvider scheduleProvider) {
        this.scheduleProvider = scheduleProvider;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}
