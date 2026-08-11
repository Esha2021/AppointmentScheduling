package AppointmentScheduling.Schedular_App.Controller;

import AppointmentScheduling.Schedular_App.Entity.ScheduleProvider;
import AppointmentScheduling.Schedular_App.Repository.ScheduleProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth/scheduleprovider")
public class ScheduleProviderController {
    @Autowired
    private ScheduleProviderRepository scheduleProviderRepository;
    @GetMapping("/all")
    public List<ScheduleProvider> createScheduleprovider() {

        return scheduleProviderRepository.findAll();
    }
   @PostMapping("/create")
   public ScheduleProvider createScheduleprovider(@RequestBody ScheduleProvider scheduleProvider) {

       return scheduleProviderRepository.save(scheduleProvider);
   }

}
