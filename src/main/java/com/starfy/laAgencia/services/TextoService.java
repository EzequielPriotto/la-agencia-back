package com.starfy.laAgencia.services;

import com.starfy.laAgencia.models.Texto;

import java.util.List;

public interface TextoService {
    Texto getById(Integer id);

    List<Texto> getAll();

    void save(Texto texto);

    void deleteAll();
}
