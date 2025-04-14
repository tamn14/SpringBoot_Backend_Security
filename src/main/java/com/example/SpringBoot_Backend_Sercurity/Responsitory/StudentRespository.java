package com.example.SpringBoot_Backend_Sercurity.Responsitory;

import com.example.SpringBoot_Backend_Sercurity.Entity.Accounts;
import com.example.SpringBoot_Backend_Sercurity.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;

@Repository
public interface RolesRespository extends JpaRepository<Roles, Integer> {
}
