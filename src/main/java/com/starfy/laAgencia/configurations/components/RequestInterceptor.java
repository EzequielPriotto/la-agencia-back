package com.starfy.laAgencia.configurations.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        if(request.getRequestURL().toString().contains("getImage") || request.getMethod().equals("OPTIONS"))
            return true;



        String xRequestID = request.getHeaders("X-REQUEST-ID").nextElement();
        logger.debug("[{}] Request entrante con el metodo: [{}]. X-REQUEST-ID: [{}], PARAMETROS: {}",
                request.getRequestURL(), request.getMethod(), xRequestID, request.getParameterMap().values().toString());

        return true;
    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // No hacer nada
    }
}
