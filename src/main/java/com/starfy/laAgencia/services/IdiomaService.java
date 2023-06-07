package com.starfy.laAgencia.services;

import com.starfy.laAgencia.models.Idioma;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IdiomaService {
    List<Idioma> mappear(List<Integer> ids);
    Idioma getById(Integer id);

    List<Idioma> getAll();

    void save(Idioma idioma);

    @Transactional
    void deleteAll();
}
