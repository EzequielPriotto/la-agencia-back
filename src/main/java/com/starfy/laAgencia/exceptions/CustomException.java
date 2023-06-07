package com.starfy.laAgencia.exceptions;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    @Getter
    @Setter
    private HttpStatus httpStatus;

    public CustomException(HttpStatus httpStatus, String mensajeError) {
        super(mensajeError);
        this.httpStatus = httpStatus;
    }

}

