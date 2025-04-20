package com.example.SpringBoot_Backend_Sercurity.DTO.Reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIReponse <T>{
    private String code ;
    private String mess ;
    private T result ;
}
