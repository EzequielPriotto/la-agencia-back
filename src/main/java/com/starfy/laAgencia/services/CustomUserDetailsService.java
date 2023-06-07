package com.starfy.laAgencia.services;

import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;

public interface CustomUserDetailsService {
    @Transactional
    UserDetails loadUserByUsername(String username);

    @Transactional
    UserDetails loadUserById(Integer id);
}
