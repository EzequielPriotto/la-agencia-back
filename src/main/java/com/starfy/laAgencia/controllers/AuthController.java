package com.starfy.laAgencia.controllers;

import com.starfy.laAgencia.dtos.AdminDTO;
import com.starfy.laAgencia.dtos.Response;
import com.starfy.laAgencia.models.Admin;
import com.starfy.laAgencia.services.AdminService;
import com.starfy.laAgencia.services.JwtTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/auth")
@Api(tags = "auth", description = "Endpoints para manejar la autenticacion")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private AdminService adminService;


    @ApiOperation(value = "Obtener token para realizar las peticiones", tags = "auth", response = AdminDTO[].class)
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestParam String username, @RequestParam String password) {

        // Autenticar al usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        // Establecer la autenticación en el contexto de seguridad de Spring
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar el token JWT
        String token = tokenService.generateToken(authentication);

        // Buscar el usuario por DNI
        Admin usuario = adminService.getByUsername(username);

        // Generar el usuarioDTO para devolverlo como repuesta
        AdminDTO admin = new AdminDTO(username, token);
        Response response = new Response("Correcto", admin, "");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/admin")
    public ResponseEntity<?> createAdmin(@RequestParam String username, @RequestParam String password) throws NoSuchAlgorithmException {
        Admin admin = new Admin(username, tokenService.cifrarMD5(password));
        adminService.save(admin);
        return ResponseEntity.ok("Se hizo pa");
    }

}
