package AppointmentScheduling.Schedular_App.Controller;

import AppointmentScheduling.Schedular_App.Dto.ProviderDTO;
import AppointmentScheduling.Schedular_App.Entity.ScheduleProvider;
import AppointmentScheduling.Schedular_App.Entity.TimeSlot;
import AppointmentScheduling.Schedular_App.Repository.AppointmentRepository;
import AppointmentScheduling.Schedular_App.Repository.ScheduleProviderRepository;
import AppointmentScheduling.Schedular_App.Repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private ScheduleProviderRepository providerRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Get all providers with analytics
    @GetMapping("/providers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ProviderDTO>> getAllProvidersWithAnalytics() {
        List<ScheduleProvider> providers = providerRepository.findAll();
        List<ProviderDTO> providerDTOs = providers.stream().map(provider -> {
            ProviderDTO dto = new ProviderDTO(
                    provider.getId(),
                    provider.getScheduleProviderName(),
                    provider.getEmailId(),
                    provider.getDescription()
            );
            long totalAppointments = appointmentRepository.countByScheduleProviderId(provider.getId());
            dto.setTotalAppointments(totalAppointments);
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(providerDTOs);
    }

    // Create new provider
    @PostMapping("/providers/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ScheduleProvider> createProvider(@RequestBody ScheduleProvider provider) {
        ScheduleProvider saved = providerRepository.save(provider);
        return ResponseEntity.ok(saved);
    }

    // Update provider
    @PutMapping("/providers/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ScheduleProvider> updateProvider(@PathVariable Long id, @RequestBody ScheduleProvider provider) {
        return providerRepository.findById(id).map(existing -> {
            existing.setScheduleProviderName(provider.getScheduleProviderName());
            existing.setEmailId(provider.getEmailId());
            existing.setDescription(provider.getDescription());
            return ResponseEntity.ok(providerRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete provider
    @DeleteMapping("/providers/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Create time slots for provider
    @PostMapping("/timeslots/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TimeSlot> createTimeSlot(@RequestBody TimeSlot timeSlot) {
        TimeSlot saved = timeSlotRepository.save(timeSlot);
        return ResponseEntity.ok(saved);
    }

    // Get analytics
    @GetMapping("/analytics")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AnalyticsDTO> getAnalytics() {
        long totalAppointments = appointmentRepository.count();
        long totalProviders = providerRepository.count();
        long totalUsers = appointmentRepository.findAll().stream()
                .map(a -> a.getUser().getId()).distinct().count();

        AnalyticsDTO analytics = new AnalyticsDTO();
        analytics.setTotalAppointments(totalAppointments);
        analytics.setTotalProviders(totalProviders);
        analytics.setTotalUsers(totalUsers);

        return ResponseEntity.ok(analytics);
    }

    // Analytics DTO
    public static class AnalyticsDTO {
        private long totalAppointments;
        private long totalProviders;
        private long totalUsers;

        public long getTotalAppointments() {
            return totalAppointments;
        }

        public void setTotalAppointments(long totalAppointments) {
            this.totalAppointments = totalAppointments;
        }

        public long getTotalProviders() {
            return totalProviders;
        }

        public void setTotalProviders(long totalProviders) {
            this.totalProviders = totalProviders;
        }

        public long getTotalUsers() {
            return totalUsers;
        }

        public void setTotalUsers(long totalUsers) {
            this.totalUsers = totalUsers;
        }
    }
}
