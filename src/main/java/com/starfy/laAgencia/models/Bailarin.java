package com.starfy.laAgencia.models;

import com.starfy.laAgencia.dtos.requests.RequestCreateBailarin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bailarin extends Persona{

    private String pais;

    private Long altura,edad;

    private Boolean tienePasaporte, esDestacado;

    public Bailarin(RequestCreateBailarin request, String codigo, List<Idioma> idiomas, List<Disponibilidad> disponibilidads, List<Rol> roles){
        super(request,codigo,idiomas, disponibilidads,roles);
        this.pais = request.getPais();
        this.tienePasaporte = request.isTienePasaporte();
        this.altura = request.getAltura();
        this.edad = request.getEdad();
        this.esDestacado = false;
    }

    public void setPais(String pais) {
        if (pais != null ) this.pais = pais;
    }

    public void setAltura(Long altura) {
        if (altura != null ) this.altura = altura;
    }
    public void setEdad(Long edad) {
        if (edad != null ) this.edad = edad;
    }
    public void setEsDestacado(Boolean esDestacado) {
        if (esDestacado != null ) this.esDestacado = esDestacado;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        if (idiomas != null) this.idiomas = idiomas;
    }

    public void setDisponibilidades(List<Disponibilidad> disponibilidads) {
        if (disponibilidads != null) this.disponibilidades = disponibilidads;
    }
    public void setRoles(List<Rol> roles) {
        if (roles != null) this.roles = roles;
    }
    public void setTienePasaporte(Boolean tienePasaporte) {if (tienePasaporte != null )  this.tienePasaporte = tienePasaporte;}

}
