package com.starfy.laAgencia.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class RequestCreateMaestro {

    @NotNull(message = "El nombre no debe estar vacío")
    private String nombre;

    private String instagram;

    private String telefono;

}
