package AppointmentScheduling.Schedular_App.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ScheduleProvider")
public class ScheduleProvider {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scheduleProviderName;
    private String emailId;
    private LocalDateTime datetime;
    private String description;

    @OneToMany(mappedBy = "scheduleProvider", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Appointment> appointment;

    public ScheduleProvider() {
    }

    public ScheduleProvider(Long id, String scheduleProviderName, String emailId, LocalDateTime datetime, String description, List<Appointment> appointment) {
        this.id = id;
        this.scheduleProviderName = scheduleProviderName;
        this.emailId = emailId;
        this.datetime = datetime;
        this.description = description;
        this.appointment = appointment;
    }

    public ScheduleProvider(Long id, String scheduleProviderName, String emailId, LocalDateTime datetime, String description) {
        this.id = id;
        this.scheduleProviderName = scheduleProviderName;
        this.emailId = emailId;
        this.datetime = datetime;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScheduleProviderName() {
        return scheduleProviderName;
    }

    public void setScheduleProviderName(String scheduleProviderName) {
        this.scheduleProviderName = scheduleProviderName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment (List<Appointment> appointment) {
        this.appointment = appointment;
    }
}
