package AppointmentScheduling.Schedular_App.Controller;


import AppointmentScheduling.Schedular_App.Dto.UserLoginRequest;
import AppointmentScheduling.Schedular_App.Dto.UserRegisterRequest;
import AppointmentScheduling.Schedular_App.Entity.Role;
import AppointmentScheduling.Schedular_App.Entity.User;
import AppointmentScheduling.Schedular_App.Repository.RoleRepository;
import AppointmentScheduling.Schedular_App.Repository.UserRepository;
import AppointmentScheduling.Schedular_App.Security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class  AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtutil;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public  AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtutil, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtutil = jwtutil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRepository.findByUsername(userRegisterRequest.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("username is already Taken");
        }
        User newUser= new User();
        newUser.setUsername(userRegisterRequest.getUsername());

        String encodedPassword=passwordEncoder.encode(userRegisterRequest.getPassword());
        newUser.setPassword(encodedPassword);

        Set<Role> roles=new HashSet<>();
        if (userRegisterRequest.getRoles() == null || userRegisterRequest.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Default role USER not found"));
            roles.add(defaultRole);
        } else {
            for (String roleName : userRegisterRequest.getRoles()) {
              Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found:" + roleName));
                roles.add(role);
            }
        }
        newUser.setRoles(roles);
        userRepository.save(newUser);
        return ResponseEntity.ok("user registerd ");

    }

    //login api
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest){

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (userLoginRequest.getUsername(), userLoginRequest.getPassword()));
        } catch (Exception e) {
            // Authentication failed - invalid credentials
        }
        String token=jwtutil.generateToken(userLoginRequest.getUsername());
        return ResponseEntity.ok(token);

    }
}
