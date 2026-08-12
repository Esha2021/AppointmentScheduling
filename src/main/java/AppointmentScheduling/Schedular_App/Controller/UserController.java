package AppointmentScheduling.Schedular_App.Controller;

import AppointmentScheduling.Schedular_App.Security.JWTutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RequestMapping("/api")
@RestController
public class  UserController {

    @Autowired
    private JWTutil jwTutil;

    @Value("${role.admin}")
    private String roleAdmin;

    @Value("${role.user}")
    private String roleUser;

     @GetMapping("/protected-data")
     public ResponseEntity<String> getProtectdData(@RequestHeader("Authorization") String token){
       if(token !=null &&token.startsWith("Bearer")){
           String jwtToken=token.substring(7);
           try{
               if(jwTutil.isTokenValid(jwtToken)){
                   String username= JWTutil.extractUsername(jwtToken);
                   Set<String> roles=jwTutil.extractRoles(jwtToken);
                   if(roles.contains(roleAdmin)){
                       return ResponseEntity.ok("Welcome"+username+"here is the"+roles+"-specific data.");
                   }else if(roles.contains(roleUser)){
                       return ResponseEntity.ok("Welcome"+username+"here is the"+roles+"-specific data.");
                   } else{
                       return ResponseEntity.status(403).body("Access Denied");
                   }
               }
           } catch(Exception ex){
               return ResponseEntity.status(HttpStatus.FORBIDDEN).body("invalid Token");
           }


       }
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header");
     }
        @GetMapping("/welcome")
        public  String Welcome(){
            return "welcome to The  diagnostic center";
        }
    }


