package com.starfy.laAgencia.services;

import com.starfy.laAgencia.dtos.Response;
import org.springframework.validation.BindingResult;

public interface ErroresService {
    Response obtenerErrores(BindingResult result);

}
