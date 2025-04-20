package com.example.SpringBoot_Backend_Sercurity.Controller;

import com.example.SpringBoot_Backend_Sercurity.DTO.Reponse.APIReponse;
import com.example.SpringBoot_Backend_Sercurity.DTO.Reponse.AuthReponse;
import com.example.SpringBoot_Backend_Sercurity.DTO.Reponse.UserReponse;
import com.example.SpringBoot_Backend_Sercurity.DTO.Request.AuthRequest;
import com.example.SpringBoot_Backend_Sercurity.DTO.Request.UserRequest;
import com.example.SpringBoot_Backend_Sercurity.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService ;

    @PostMapping("/login")
    public ResponseEntity<APIReponse<AuthReponse>> login(@RequestBody AuthRequest request) {
        AuthReponse response = authService.login(request);
        return ResponseEntity.ok(new APIReponse<>("Success", "Login successfully", response));
    }

    @PostMapping("/signup")
    public ResponseEntity<APIReponse<UserReponse>> signup(@RequestBody UserRequest request) {
        UserReponse response = authService.register(request);
        return ResponseEntity.ok(new APIReponse<UserReponse>("Success", "User registered successfully", response));
    }
}
