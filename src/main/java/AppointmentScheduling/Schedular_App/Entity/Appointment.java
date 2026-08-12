package AppointmentScheduling.Schedular_App.Entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Table(name="Appointment")
public class Appointment {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String AppointeeName;
    private String Appointee_emailId;
    private LocalDateTime datetime;

    private String location;
    @Enumerated(EnumType.STRING)//stores status as a string
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    //@JsonDeserialize(using = UserDeserializer.class)
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_provider_id", nullable = false)
    private ScheduleProvider scheduleProvider;

        public Appointment() {
    }

    public Appointment(String AppointeeName, String Appointee_emailId, LocalDateTime datetime, String location, Status status) {
        this.AppointeeName = AppointeeName;
        this.Appointee_emailId = Appointee_emailId;
        this.datetime = datetime;
        this.location = location;
        this.status = status;
    }

    public String getAppointeeName() {
        return AppointeeName;
    }

    public void setAppointeeName(String appointeeName) {
        AppointeeName = appointeeName;
    }

    public String getAppointee_emailId() {
        return Appointee_emailId;
    }

    public void setAppointee_emailId(String appointee_emailId) {
        Appointee_emailId = appointee_emailId;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public Appointment( String AppointeeName, String Appointee_emailId, LocalDateTime datetime, String location, Status status) {
//
//        this.AppointeeName = AppointeeName;
//        this.Appointee_emailId = Appointee_emailId;
//       this. datetime = datetime;
//        this.location = location;
//        this.status = status;
//    }


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
//
//    public void setUser(Long userId) {
//            this.user=new User(userId);
//    }
}
