package com.example.SpringBoot_Backend_Sercurity.Service;

import com.example.SpringBoot_Backend_Sercurity.DTO.Reponse.AuthReponse;
import com.example.SpringBoot_Backend_Sercurity.DTO.Reponse.UserReponse;
import com.example.SpringBoot_Backend_Sercurity.DTO.Request.AuthRequest;
import com.example.SpringBoot_Backend_Sercurity.DTO.Request.UserRequest;

public interface AuthService {
    AuthReponse login(AuthRequest request);
    UserReponse register(UserRequest request);
}
