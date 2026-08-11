package AppointmentScheduling.Schedular_App.Repository;

import AppointmentScheduling.Schedular_App.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User,Long> {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
}
