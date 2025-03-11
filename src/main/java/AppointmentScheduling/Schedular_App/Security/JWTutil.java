package AppointmentScheduling.Schedular_App.Security;


import AppointmentScheduling.Schedular_App.Entity.Role;
import AppointmentScheduling.Schedular_App.Entity.User;
import AppointmentScheduling.Schedular_App.Repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public  class JWTutil {
    private static final SecretKey secretKey= Keys.secretKeyFor(SignatureAlgorithm.HS512);//randomly generate a key by using hs512 algorithm

    private static final long EXPIRATION_TIME = 864_000_000;

    private  UserRepository userRepository;

    public JWTutil(UserRepository userRepository) {
        this.userRepository = userRepository;

           }


    public  String generateToken(String username) {
        Optional<User> user= userRepository.findByUsername(username);
        Set<Role> roles=user.get().getRoles();
//Add roles to token
        return Jwts.builder()
                .setSubject(username).claim("roles",roles.stream()
                .map(role->role.getName()).collect(Collectors.joining(",")))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+ EXPIRATION_TIME))
                .signWith( secretKey)
                .compact();
    }

    //Extract Username
    public static String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //Extract Roles

    public Set<String> extractRoles(String token){
        String rolesString = (String) Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().get("roles",String.class);
        return Set.of(rolesString);
    }


    //Token Validation
   public Boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return  true;
        }catch (JwtException |IllegalArgumentException e){
            return false;
        }
   }

}
