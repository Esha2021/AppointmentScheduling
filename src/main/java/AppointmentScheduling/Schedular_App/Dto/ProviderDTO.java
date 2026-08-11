package AppointmentScheduling.Schedular_App.Dto;

public class ProviderDTO {
    private Long id;
    private String scheduleProviderName;
    private String emailId;
    private String description;
    private Long totalAppointments;
    private Long completedAppointments;

    public ProviderDTO() {
    }

    public ProviderDTO(Long id, String scheduleProviderName, String emailId, String description) {
        this.id = id;
        this.scheduleProviderName = scheduleProviderName;
        this.emailId = emailId;
        this.description = description;
        this.totalAppointments = 0L;
        this.completedAppointments = 0L;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(Long totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public Long getCompletedAppointments() {
        return completedAppointments;
    }

    public void setCompletedAppointments(Long completedAppointments) {
        this.completedAppointments = completedAppointments;
    }
}
