package AppointmentScheduling.Schedular_App.Controller;

import AppointmentScheduling.Schedular_App.Entity.TimeSlot;
import AppointmentScheduling.Schedular_App.Repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timeslot")
@CrossOrigin(origins = "http://localhost:3000")
public class TimeSlotController {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @GetMapping("/available/{providerId}")
    public List<TimeSlot> getAvailableSlots(@PathVariable Long providerId) {
        return timeSlotRepository.findByScheduleProviderIdAndIsBookedFalse(providerId);
    }

    @PostMapping("/create")
    public TimeSlot createTimeSlot(@RequestBody TimeSlot timeSlot) {
        return timeSlotRepository.save(timeSlot);
    }
}
