package com.starfy.laAgencia.dtos;

import com.starfy.laAgencia.models.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BailarinPanelDTO extends BailarinDTO {

    private String instagram,telefono,nombre;


    public BailarinPanelDTO(Bailarin bailarin){
        this.codigo =  bailarin.getCodigo();
        this.altura = bailarin.getAltura();
        this.idiomas = bailarin.getIdiomas().stream().map(Idioma::getNombre).collect(Collectors.toList());
        this.disponibilidades = bailarin.getDisponibilidades().stream().map(Disponibilidad::getValor).collect(Collectors.toList());
        this.roles = bailarin.getRoles().stream().map(Rol::getValor).collect(Collectors.toList());
        this.pasaporte = bailarin.getTienePasaporte();
        this.instagram = bailarin.getInstagram();
        this.telefono = bailarin.getTelefono();
        this.nombre = bailarin.getNombre();
        this.fotos =bailarin.getFotos().stream().map(Foto::getUrl).collect(Collectors.toList());
        this.edad = bailarin.getEdad();
        this.pais = bailarin.getPais();
        this.id = bailarin.getId();
    }

}
