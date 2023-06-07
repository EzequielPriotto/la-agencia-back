package com.starfy.laAgencia.dtos;

import com.starfy.laAgencia.models.Admin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTO {

    private String username, token;

    public AdminDTO(Admin admin){
        this.username = admin.getUsername();
    }

    public AdminDTO(String username, String token){
        this.username = username;
        this.token = token;
    }

}
