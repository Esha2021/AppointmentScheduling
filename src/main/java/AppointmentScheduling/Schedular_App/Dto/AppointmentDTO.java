package AppointmentScheduling.Schedular_App.Dto;

import AppointmentScheduling.Schedular_App.Entity.Appointment;

import java.time.LocalDateTime;

public class AppointmentDTO {

    private Long id;
    private String appointeeName;
    private String appointeeEmailId;
    private LocalDateTime datetime;
    private String location;
    private String status;
    private String userName;        // Extracted from User entity
    private String providerName;    // Extracted from ScheduleProvider entity

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.appointeeName = appointment.getAppointeeName();
        this.appointeeEmailId = appointment.getAppointee_emailId();
        this.datetime = appointment.getDatetime();
        this.location = appointment.getLocation();
        this.status = appointment.getStatus().toString();
        this.userName = appointment.getUser().getUsername(); // Assuming User has getUserName()
        this.providerName = appointment.getScheduleProvider().getScheduleProviderName(); // Assuming ScheduleProvider has getSchedulProviderName()
    }


    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppointeeName() {
        return appointeeName;
    }

    public void setAppointeeName(String appointeeName) {
        this.appointeeName = appointeeName;
    }

    public String getAppointeeEmailId() {
        return appointeeEmailId;
    }

    public void setAppointeeEmailId(String appointeeEmailId) {
        this.appointeeEmailId = appointeeEmailId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

}
