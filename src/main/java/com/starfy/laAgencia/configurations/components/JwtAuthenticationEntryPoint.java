package com.starfy.laAgencia.configurations.components;

import com.google.gson.Gson;
import com.starfy.laAgencia.dtos.Response;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Component
    public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             AuthenticationException authException) throws IOException {
            response.setContentType("application/json");
            response.setStatus(401);
            PrintWriter out = response.getWriter();

            Response responsePersonalizada = new Response("Error", "", "Se esta realizando una peticion sin autorizacion");
            String personaJson = new Gson().toJson(responsePersonalizada);

            out.println(personaJson);
            out.close();
            out.flush();
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Se esta realizando una peticion sin autorizacion");

        }

}
