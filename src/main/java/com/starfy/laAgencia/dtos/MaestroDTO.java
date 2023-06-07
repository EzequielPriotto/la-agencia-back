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
public class MaestroDTO {
    protected Integer id;
    protected String codigo;
    protected String nombre;
    protected List<String> fotos = new ArrayList<>();

    public MaestroDTO(Maestro maestro) {
        this.nombre = maestro.getNombre();
        this.codigo =  maestro.getCodigo();
        this.fotos =maestro.getFotos().stream().map(Foto::getUrl).collect(Collectors.toList());
        this.id = maestro.getId();
    }
}
