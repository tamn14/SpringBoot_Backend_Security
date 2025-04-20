package com.example.SpringBoot_Backend_Sercurity.Responsitory;

import com.example.SpringBoot_Backend_Sercurity.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles , Integer> {
    Optional<Roles> findByName(String name) ;
}
