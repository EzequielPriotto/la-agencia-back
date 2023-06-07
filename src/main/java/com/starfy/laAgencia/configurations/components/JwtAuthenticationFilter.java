package com.starfy.laAgencia.configurations.components;

import com.starfy.laAgencia.exceptions.CustomException;
import com.starfy.laAgencia.services.CustomUserDetailsService;
import com.starfy.laAgencia.services.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    // Sobreescribo el metodo por defecto para poder poner mis propias reglas de login
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // Obtener el token del header "Authorization" de la peticion
            String jwt = tokenService.obtenerJwt(request);

            if (StringUtils.hasText(jwt) && tokenService.validateToken(jwt)) { // validamos que no este null y que
                // ademas la firma con la que se armo el token sea nuestra

                Integer userId = tokenService.getUserIdFromJWT(jwt); // Obtener el ID de usuario del token JWT


                UserDetails userDetails = customUserDetailsService.loadUserById(userId);  //Cargar los detalles del
                // usuario utilizando el ID de usuario

                // Establecer la autenticación en el contexto de seguridad para la solicitud actual
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("No se pudo establecer la autenticación de usuario en el contexto de seguridad");
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo establecer la autenticación de " +
                    "usuario en el contexto de seguridad");
        }
        filterChain.doFilter(request, response);
    }


}
