package com.starfy.laAgencia.services.implementations;

import com.starfy.laAgencia.models.Rol;
import com.starfy.laAgencia.repositories.RolRepository;
import com.starfy.laAgencia.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RolServiceImpl implements RolService {

    @Autowired
    RolRepository rolRepository;

    @Override
    public List<Rol> getAll() {
        return rolRepository.findAll();
    }

    @Override
    public List<Rol> mappear(List<Integer> ids) {
        List<Rol> roles = ids.stream().map(id-> rolRepository.findById(id).orElse(null)).collect(Collectors.toList());
        return roles;
    }

    @Override
    public void save(Rol rol){
        rolRepository.save(rol);
    }
    @Override
    public void deleteAll(){
        rolRepository.deleteAll();
    }

}