package AppointmentScheduling.Schedular_App.Controller;


import AppointmentScheduling.Schedular_App.Dto.AppointmentDTO;
import AppointmentScheduling.Schedular_App.Entity.Appointment;
import AppointmentScheduling.Schedular_App.Repository.AppointmentRepository;
import AppointmentScheduling.Schedular_App.Repository.ScheduleProviderRepository;
import AppointmentScheduling.Schedular_App.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("appointment")
@RestController
public class AppointmentController {
    @Autowired
    public AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private ScheduleProviderRepository scheduleProviderRepository;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/all")
    public List<Appointment> getAllAppoitment(){
        return appointmentRepository.findAll();
    }

//    @PreAuthorize("hasAuthority('USER')")
//    @PostMapping("/create")
//    public Appointment creatAppointment(@RequestBody Appointment appointment){
//
//        return  appointmentRepository.save(appointment);
//    }

//    @PostMapping("/book")
//    public String bookAppointment(@RequestBody Appointment appointment) {
//        String response = appointmentService.bookAppointment(appointment.getDatetime()&&scheduleP);
//        return "appointment booked sucessfully";
//    }
@PostMapping("/book")
public ResponseEntity<AppointmentDTO> bookAppointment(@RequestParam Long userId, @RequestParam Long scheduleProviderId,@RequestParam String appointeeName ,
                                                      @RequestParam String appointeeEmailId,
                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat. ISO.DATE_TIME) LocalDateTime datetime,@RequestParam String location){
    AppointmentDTO appointmentDTO = appointmentService.bookAppointment(userId, scheduleProviderId,appointeeName,appointeeEmailId, datetime,location);
    return ResponseEntity.ok(appointmentDTO);
}

}
