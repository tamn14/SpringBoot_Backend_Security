package com.example.SpringBoot_Backend_Sercurity.Responsitory;

import com.example.SpringBoot_Backend_Sercurity.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users , Integer> {
    Optional<Users> findByUserName(String username) ;
    boolean existsByUserName(String username) ;
}
