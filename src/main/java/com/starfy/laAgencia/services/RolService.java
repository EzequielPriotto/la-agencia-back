package com.starfy.laAgencia.services;

import com.starfy.laAgencia.models.Rol;

import java.util.List;

public interface RolService {
    List<Rol> getAll();
    List<Rol> mappear(List<Integer> ids);
    void save(Rol rol);

    void deleteAll();
}
