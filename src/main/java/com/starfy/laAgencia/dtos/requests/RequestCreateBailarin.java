package com.starfy.laAgencia.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class RequestCreateBailarin {

    @NotNull(message = "El nombre no debe estar vacío")
    private String nombre;

    @NotNull(message = "El instagram no debe estar vacío")
    private String instagram;

    @NotNull(message = "El telefono no debe estar vacío")
    private String telefono;

    @NotNull(message = "La altura no debe estar vacío")
    private Long altura;

    @NotNull(message = "El pais no debe estar vacío")
    private String pais;

    @NotNull(message = "La edad no debe estar vacía")
    private Long edad;

    @Size(min=1, message = "Los idiomas deben ser al menos uno")
    @NotNull(message = "Los idiomas no deben estar vacíos")
    private List<@NotNull(message = "Los idiomas no deben estar vacíos") Integer> idiomas;

    @Size(min=1, message = "Las disponibilidades deben ser al menos una")
    @NotNull(message = "Las disponibilidades no deben estar vacías")
    private List<@NotNull(message = "Las disponibilidades no deben estar vacías") Integer> disponibilidades;

    @Size(min=1, message = "Los roles deben ser al menos uno")
    @NotNull(message = "Los roles no deben estar vacíos")
    private List<@NotNull(message = "Los roles no deben estar vacíos") Integer> roles;

    @JsonProperty("tiene_pasaporte")
    private boolean tienePasaporte;

    @JsonProperty("es_destacado")
    private boolean esDestacado;

}
