package com.example.SpringBoot_Backend_Sercurity.DTO.Reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReponse {
    private String userName ;
    private String passWord ;
    private String fullName ;
    private List<String> roles = new ArrayList<>();
}
