package com.starfy.laAgencia.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Getter
@Setter
public class RequestCreateTexto {

        @NotNull(message = "El id del idioma no debe estar vacío")
        private Integer idioma;

        @NotNull(message = "El id del idioma no debe estar vacío")
        private String texto;
}
