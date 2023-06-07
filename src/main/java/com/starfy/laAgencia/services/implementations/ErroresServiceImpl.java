package com.starfy.laAgencia.services.implementations;

import com.starfy.laAgencia.dtos.Response;
import com.starfy.laAgencia.services.ErroresService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ErroresServiceImpl implements ErroresService {

    @Override
    public Response obtenerErrores(BindingResult result) {
        List<String> errores = result.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new Response("Bad Request", "", errores);
    }

}
