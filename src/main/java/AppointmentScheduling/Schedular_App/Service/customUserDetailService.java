package AppointmentScheduling.Schedular_App.Service;

import AppointmentScheduling.Schedular_App.Entity.User;
import AppointmentScheduling.Schedular_App.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class customUserDetailService implements UserDetailsService {

    private final UserRepository userRepository ;


 @Autowired
 public customUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found:"+username));

        return new org.springframework.security.core.userdetails.User(//spring security 'user' object implements userdetail
                user.getUsername(),   //fetches username,password,
                user.getPassword(),

                user.getRoles().//retrive the roles and covert them into a simplegrantedAuthority objects
                        stream().
                        map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }
}
