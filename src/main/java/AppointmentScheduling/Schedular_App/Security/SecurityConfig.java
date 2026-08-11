package AppointmentScheduling.Schedular_App.Security;


import AppointmentScheduling.Schedular_App.Service.customUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    private JwtAuthFilter authFilter;

    @Autowired
    public SecurityConfig(JwtAuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public UserDetailsService userDetailsService(customUserDetailService customUserDetailService) {
        return customUserDetailService; // Ensure UserInfoService implements UserDetailsService
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http.csrf(csrf -> csrf.disable())// Disable CSRF for stateless APIs

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/user/**").hasAuthority("USER")
                        .requestMatchers("/appointment/**").permitAll()
                        .requestMatchers("/timeslot/**").permitAll()
                        .anyRequest().authenticated() // Protect all other endpoints
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions
                )
                .authenticationProvider(authenticationProvider(userDetailsService))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Add JWT filter

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoding
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
              CorsConfiguration ccfg = new CorsConfiguration();
              ccfg.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
              ccfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
              ccfg.setAllowCredentials(true);
              ccfg.setAllowedHeaders(Collections.singletonList("*"));
              ccfg.setExposedHeaders(Arrays.asList("Authorization"));
              ccfg.setMaxAge(3600L);
              source.registerCorsConfiguration("/**", ccfg);
              return  source;
                     }



}