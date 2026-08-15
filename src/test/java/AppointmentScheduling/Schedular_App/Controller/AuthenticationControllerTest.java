package AppointmentScheduling.Schedular_App.Controller;

import AppointmentScheduling.Schedular_App.Dto.UserLoginRequest;
import AppointmentScheduling.Schedular_App.Dto.UserRegisterRequest;
import AppointmentScheduling.Schedular_App.Entity.Role;
import AppointmentScheduling.Schedular_App.Entity.User;
import AppointmentScheduling.Schedular_App.Repository.RoleRepository;
import AppointmentScheduling.Schedular_App.Repository.UserRepository;
import AppointmentScheduling.Schedular_App.Security.JWTutil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTutil jwtutil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterNewUser() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("testuser");
        request.setPassword("password123");
        request.setRoles(new HashSet<>());

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(new Role(1L, "USER")));
        when(userRepository.save(any(User.class))).thenReturn(new User());

        ResponseEntity<String> response = authenticationController.register(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("registerd"));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserAlreadyExists() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("existinguser");
        request.setPassword("password123");

        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(new User()));

        ResponseEntity<String> response = authenticationController.register(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("already Taken"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginSuccess() {
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("testuser");
        request.setPassword("password123");

        when(jwtutil.generateToken("testuser")).thenReturn("mockToken123");

        ResponseEntity<String> response = authenticationController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("mockToken123", response.getBody());
    }
}
