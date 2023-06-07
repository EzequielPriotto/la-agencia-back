package com.starfy.laAgencia.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class RequestUpdateMaestro {

    @NotNull(message = "El id no debe estar vacío")
    private Integer id;

    private String nombre,instagram,telefono;

}


