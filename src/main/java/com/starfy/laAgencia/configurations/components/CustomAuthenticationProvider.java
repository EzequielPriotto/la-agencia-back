package com.starfy.laAgencia.configurations.components;

import com.starfy.laAgencia.services.CustomUserDetailsService;
import com.starfy.laAgencia.services.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    JwtTokenService jwtTokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Obtener los detalles del usuario
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Cifro el id (en MD5 )
        String hashedPassword = "";
        try{
            hashedPassword = jwtTokenService.cifrarMD5(password);
        }catch (Exception e){
            logger.error(e.toString());
        }

        // Validar la contraseña
        if (!hashedPassword.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Credenciales incorrectas");
        }

        // Crear una instancia de UsernamePasswordAuthenticationToken con los detalles del usuario autenticado
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }





}
