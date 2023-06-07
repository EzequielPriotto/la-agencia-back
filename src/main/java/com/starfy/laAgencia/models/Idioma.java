package com.starfy.laAgencia.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;

    private String nombre;

    private String nombreCorto;

    @OneToOne(mappedBy = "idioma", cascade = CascadeType.REMOVE)
    private Texto texto;

    @ManyToMany(mappedBy = "idiomas", cascade = CascadeType.REMOVE)
    private List<Persona> personas = new ArrayList<>();

}
