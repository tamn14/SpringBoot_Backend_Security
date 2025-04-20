package com.example.SpringBoot_Backend_Sercurity.Service;

import com.example.SpringBoot_Backend_Sercurity.DTO.Reponse.AuthReponse;
import com.example.SpringBoot_Backend_Sercurity.DTO.Reponse.UserReponse;
import com.example.SpringBoot_Backend_Sercurity.DTO.Request.AuthRequest;
import com.example.SpringBoot_Backend_Sercurity.DTO.Request.UserRequest;
import com.example.SpringBoot_Backend_Sercurity.Entity.Roles;
import com.example.SpringBoot_Backend_Sercurity.Entity.Users;
import com.example.SpringBoot_Backend_Sercurity.Mapper.mapper;
import com.example.SpringBoot_Backend_Sercurity.Responsitory.RolesRepository;
import com.example.SpringBoot_Backend_Sercurity.Responsitory.UserRepository;
import com.example.SpringBoot_Backend_Sercurity.Security_Config.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private mapper map;


    @Override
    public AuthReponse login(AuthRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));
        return new AuthReponse(tokenProvider.generateToken(authentication) , true) ;
    }

    @Override
    public UserReponse register(UserRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new RuntimeException("Username is already taken");
        }

        // Tìm danh sách Role từ tên
        Set<Roles> userRoles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found")))
                .collect(Collectors.toSet());

        // Tạo user entity
        Users user = map.toEntity(request);

        // Lưu user vào DB
        userRepository.saveAndFlush(user);

        return map.toDto(user) ;
    }

}
