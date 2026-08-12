package AppointmentScheduling.Schedular_App.Repository;

import AppointmentScheduling.Schedular_App.Entity.Role;
import AppointmentScheduling.Schedular_App.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository <Role,Long>{
    Optional<Role> findByName(String name);
}
