package com.starfy.laAgencia.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starfy.laAgencia.dtos.AdminDTO;
import com.starfy.laAgencia.models.Admin;
import com.starfy.laAgencia.services.AdminService;
import com.starfy.laAgencia.services.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {


    private static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    @Value("${app.jwtKeySecret}")
    private String jwtSecret;

    @Value("${app.jwtExpiration}")
    private int jwtExpiration;

    @Autowired
    AdminService adminService;


    @Override
    public String obtenerJwt(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // si tiene texto y ademas empieza con Bearer devuelvo el token sin Bearer
        }
        return null;
    }

    @Override
    public String generateToken(Authentication authentication) {

        UserDetails infoLogin = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date fechaExpiracion = new Date(now.getTime() + jwtExpiration);

        Admin admin = adminService.getByUsername(infoLogin.getUsername());
        AdminDTO adminDTO = new AdminDTO(admin);

        return Jwts.builder()
                .setSubject(admin.getId().toString())
                .claim("admin",adminDTO)
                .setIssuedAt(new Date())
                .setExpiration(fechaExpiracion)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    @Override
    public Integer getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }

    @Override
    public AdminDTO getInfoFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        AdminDTO admin = objectMapper.convertValue(claims.get("admin"), AdminDTO.class);
        return admin;
    }

    @Override
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            logger.error("Firma JWT no válida", ex);
        }
        return false;
    }

    @Override
    public String cifrarMD5(String password) throws NoSuchAlgorithmException {
        MessageDigest cifrador = MessageDigest.getInstance("MD5");
        cifrador.update(password.getBytes());
        byte[] bytes = cifrador.digest();
        StringBuilder passwordEncriptada = new StringBuilder();
        for (byte bite : bytes) {
            passwordEncriptada.append(String.format("%02x", bite));
        }
        return passwordEncriptada.toString();
    }

}
