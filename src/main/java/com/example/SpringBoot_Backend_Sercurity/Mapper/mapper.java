package com.example.SpringBoot_Backend_Sercurity.Mapper;

import com.example.SpringBoot_Backend_Sercurity.DTO.Reponse.UserReponse;
import com.example.SpringBoot_Backend_Sercurity.DTO.Request.UserRequest;
import com.example.SpringBoot_Backend_Sercurity.Entity.Roles;
import com.example.SpringBoot_Backend_Sercurity.Entity.Users;
import com.example.SpringBoot_Backend_Sercurity.Responsitory.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class mapper {
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // DTO → Entity
    public Users toEntity(UserRequest dto) {
        List<Roles> roleList = dto.getRoles().stream()
                .map(roleName -> rolesRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toList());

        Users user = new Users();
        user.setUserName(dto.getUserName());
        user.setPassword(passwordEncoder.encode(dto.getPassWord())); // mã hóa password
        user.setFullName(dto.getFullName());
        user.setRoles(roleList);

        return user;
    }

    // Entity → DTO
    public UserReponse toDto(Users user) {
        List<String> roles = user.getRoles().stream()
                .map(Roles::getName)
                .collect(Collectors.toList());

        return new UserReponse(
                user.getUserName(),
                user.getFullName(),
                user.getEmail(),
                roles
        );
    }
}
