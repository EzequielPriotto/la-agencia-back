package com.starfy.laAgencia.models;


import com.starfy.laAgencia.dtos.requests.RequestCreateBailarin;
import com.starfy.laAgencia.dtos.requests.RequestCreateMaestro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    protected Integer id;

    protected String codigo, nombre, telefono,instagram;

    @ManyToMany()
    @JoinTable(
            name = "persona_idioma",
            joinColumns = @JoinColumn(name = "persona_id"),
            inverseJoinColumns = @JoinColumn(name = "idioma_id")
    )
    protected List<Idioma> idiomas = new ArrayList<>();

    @ManyToMany()
    @JoinTable(
            name = "persona_disponibilidad",
            joinColumns = @JoinColumn(name = "persona_id"),
            inverseJoinColumns = @JoinColumn(name = "disponibilidad_id")
    )
    protected List<Disponibilidad> disponibilidades = new ArrayList<>();

    @ManyToMany()
    @JoinTable(
            name = "persona_rol",
            joinColumns = @JoinColumn(name = "persona_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    protected List<Rol> roles = new ArrayList<>();

    @OneToMany(mappedBy="persona", fetch=FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
    protected List<Foto> fotos = new ArrayList<>();


    public Persona(RequestCreateBailarin request, String codigo, List<Idioma> idiomas, List<Disponibilidad> disponibilidades, List<Rol> roles){
        this.codigo = codigo;
        this.nombre = request.getNombre();
        this.telefono = request.getTelefono();
        this.instagram = request.getInstagram();
        disponibilidades.forEach(this::addDisponibilidad);
        idiomas.forEach(this::addIdioma);
        roles.forEach(this::addRol);
    }
    public Persona(RequestCreateMaestro request, String codigo){
        this.codigo = codigo;
        this.nombre = request.getNombre();
        this.telefono = request.getTelefono();
        this.instagram = request.getInstagram();
    }


    public void setId(Integer id) {
        if (id != null ) this.id = id;
    }

    public void setCodigo(String codigo) {
        if (codigo != null )  this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        if (nombre != null ) this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        if (telefono != null ) this.telefono = telefono;
    }

    public void setInstagram(String instagram) {
        if (instagram != null ) this.instagram = instagram;
    }

    public void addFoto(Foto foto){
        if (foto != null ) fotos.add(foto);
    }

    public void addIdioma(Idioma idioma) {
        if (idioma != null ) {
            this.idiomas.add(idioma);
            idioma.getPersonas().add(this);
        }
    }

    public void addDisponibilidad(Disponibilidad disponibilidad) {
        if (disponibilidad != null ) {
            this.disponibilidades.add(disponibilidad);
            disponibilidad.getPersonas().add(this);
        }
    }

    public void addRol(Rol rol) {
        if (rol != null ) {
            this.roles.add(rol);
            rol.getPersonas().add(this);
        }
    }

    public void removeFoto(Foto foto){
        this.fotos.remove(foto);
    }


}

