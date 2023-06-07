package com.starfy.laAgencia.services;

import com.starfy.laAgencia.models.Disponibilidad;

import java.util.List;

public interface DisponibilidadService {
    List<Disponibilidad> getAll();
    List<Disponibilidad> mappear(List<Integer> ids);
    void save(Disponibilidad disponibilidad);

    void deleteAll();
}
