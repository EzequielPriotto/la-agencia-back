package com.starfy.laAgencia.services;

import com.starfy.laAgencia.models.Bailarin;
import com.starfy.laAgencia.models.Maestro;

import java.util.List;

public interface MaestroService {
    List<Maestro> getAll();

    Maestro getById(Integer id);

    Maestro getByCodigo(String code);

    void save(Maestro maestro);
    void delete(Maestro maestro);
    void deleteAll();
}
