package com.starfy.laAgencia.models;

import com.starfy.laAgencia.dtos.requests.RequestCreateBailarin;
import com.starfy.laAgencia.dtos.requests.RequestCreateMaestro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Maestro extends Persona {

    public Maestro(RequestCreateMaestro request, String codigo){
        super(request,codigo);
    }
}
