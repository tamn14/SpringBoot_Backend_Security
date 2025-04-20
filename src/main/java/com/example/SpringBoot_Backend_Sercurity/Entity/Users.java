package com.example.SpringBoot_Backend_Sercurity.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;
    @Column(name = "fullname")
    private String fullName;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String password;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.ALL
            }

    )
    @JoinTable(
            name = "user_role" ,
            joinColumns = @JoinColumn(name = "usersID"),
            inverseJoinColumns = @JoinColumn(name = "RoleID")
    )
    private List<Roles> roles = new ArrayList<>() ;


    // Synchronizes data
    public void addRoles(Roles roles) {
        this.roles.add(roles) ;
        roles.getUsers().add(this);
    }
    public void removeRoles(Roles roles) {
        this.roles.remove(roles) ;
        roles.getUsers().remove(this);
    }
}
