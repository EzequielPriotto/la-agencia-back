package com.starfy.laAgencia.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.starfy.laAgencia.models.Idioma;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdiomaDTO extends ClaveValorDTO {
    @JsonProperty("nombre_corto")
    String valorCorto;

    public IdiomaDTO(Idioma idioma){
        this.id = idioma.getId();
        this.valor = idioma.getNombre();
        this.valorCorto = idioma.getNombreCorto();
    }

}
