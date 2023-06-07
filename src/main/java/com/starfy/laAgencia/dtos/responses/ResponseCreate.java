package com.starfy.laAgencia.dtos.responses;

import com.starfy.laAgencia.models.Persona;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCreate {

    private Integer id;
    private String codigo;

    public ResponseCreate(Persona persona){
        this.id = persona.getId();
        this.codigo = persona.getCodigo();
    }
}
