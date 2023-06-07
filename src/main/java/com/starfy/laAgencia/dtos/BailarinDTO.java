package com.starfy.laAgencia.dtos;

import com.starfy.laAgencia.models.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BailarinDTO {
    protected Integer id;
    protected String codigo, pais;
    protected Long altura, edad;
    protected List<String> idiomas, disponibilidades, roles;
    protected Boolean pasaporte;

    protected List<String> fotos = new ArrayList<>();

    public BailarinDTO(Bailarin bailarin) {
        this.codigo =  bailarin.getCodigo();
        this.altura = bailarin.getAltura();
        this.idiomas = bailarin.getIdiomas().stream().map(Idioma::getNombre).collect(Collectors.toList());
        this.disponibilidades = bailarin.getDisponibilidades().stream().map(Disponibilidad::getValor).collect(Collectors.toList());
        this.roles = bailarin.getRoles().stream().map(Rol::getValor).collect(Collectors.toList());
        this.pasaporte = bailarin.getTienePasaporte();
        this.fotos =bailarin.getFotos().stream().map(Foto::getUrl).collect(Collectors.toList());
        this.edad = bailarin.getEdad();
        this.pais = bailarin.getPais();
        this.id = bailarin.getId();
    }
}
