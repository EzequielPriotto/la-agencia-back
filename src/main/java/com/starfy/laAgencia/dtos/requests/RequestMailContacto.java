package com.starfy.laAgencia.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class RequestMailContacto {

    @NotNull(message = "El nombre no debe estar vacío")
    private String nombre;

    @NotNull(message = "El telefono no debe estar vacío")
    private String telefono;

    @NotNull(message = "El mail no debe estar vacío")
    private String mail;

    @NotNull(message = "El mensaje no debe estar vacío")
    private String mensaje;

}
