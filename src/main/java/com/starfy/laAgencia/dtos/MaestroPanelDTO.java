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
public class MaestroPanelDTO extends MaestroDTO {

    private String instagram,telefono,nombre;

    public MaestroPanelDTO(Maestro maestro){
        this.codigo =  maestro.getCodigo();
        this.instagram = maestro.getInstagram();
        this.telefono = maestro.getTelefono();
        this.nombre = maestro.getNombre();
        this.fotos =maestro.getFotos().stream().map(Foto::getUrl).collect(Collectors.toList());
        this.id = maestro.getId();
    }

}
