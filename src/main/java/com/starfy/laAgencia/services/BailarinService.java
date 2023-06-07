package com.starfy.laAgencia.services;

import com.starfy.laAgencia.models.Bailarin;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BailarinService {
    List<Bailarin> getAll();

    List<Bailarin> getDestacados();

    Bailarin getById(Integer id);

    Bailarin getByCodigo(String code);


    void save(Bailarin bailarin);
    void delete(Bailarin bailarin);
    void deleteAll();
}
