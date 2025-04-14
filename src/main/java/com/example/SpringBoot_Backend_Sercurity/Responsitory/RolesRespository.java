package com.example.SpringBoot_Backend_Sercurity.Responsitory;

import com.example.SpringBoot_Backend_Sercurity.Entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRespository extends JpaRepository<Accounts , Integer> {
}
