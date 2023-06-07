package com.starfy.laAgencia.services.implementations;

import com.starfy.laAgencia.exceptions.CustomException;
import com.starfy.laAgencia.models.Admin;
import com.starfy.laAgencia.services.AdminService;
import com.starfy.laAgencia.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private AdminService adminService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {

        Admin usuario = adminService.getByUsername(username); // Buscamos el usuario por el dni en la base

        if (usuario == null) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Credenciales erroneas");
        }

        // Creamos el UserDetails en base a los datos del usuario ( es como un dto)
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(new ArrayList<>())
                .build();
    }
    @Override
    @Transactional
    public UserDetails loadUserById(Integer id) {

        Admin usuario = adminService.getById(id);  // Buscamos el usuario por id en la base

        if (usuario == null) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Credenciales erroneas");
        }

        // Creamos el UserDetails en base a los datos del usuario ( es como un dto)
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(new ArrayList<>())
                .build();
    }

}
