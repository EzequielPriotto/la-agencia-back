package com.starfy.laAgencia.services;

import com.starfy.laAgencia.models.Foto;

public interface FotoService {
    Foto getById(Integer id);

    Foto getByBailarin(Integer id);
    void save(Foto foto);
    void delete(Foto foto);
}
