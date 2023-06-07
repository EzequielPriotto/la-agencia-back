package com.starfy.laAgencia.services;

import com.starfy.laAgencia.dtos.AdminDTO;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

public interface JwtTokenService {
    String obtenerJwt(HttpServletRequest request);

    String generateToken(Authentication authentication);

    Integer getUserIdFromJWT(String token);

    AdminDTO getInfoFromJWT(String token);

    boolean validateToken(String authToken);

    String cifrarMD5(String password) throws NoSuchAlgorithmException;
}
