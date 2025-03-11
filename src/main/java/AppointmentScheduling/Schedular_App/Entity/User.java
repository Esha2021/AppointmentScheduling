package AppointmentScheduling.Schedular_App.Entity;


import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Users")
public class User {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String username;
    private String  password;

    @ManyToMany(fetch= FetchType.EAGER,cascade = CascadeType.PERSIST)// store the role while creating user in db
    private Set<Role> roles=new HashSet<>();//Many users have multiple roles

    public User() {
    }

    public User(long id, String username, String password, Set<Role> roles) {
        Id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
