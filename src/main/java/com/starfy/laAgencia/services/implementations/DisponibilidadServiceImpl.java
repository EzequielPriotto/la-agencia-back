package com.starfy.laAgencia.services.implementations;

import com.starfy.laAgencia.models.Disponibilidad;
import com.starfy.laAgencia.repositories.DisponibilidadRepository;
import com.starfy.laAgencia.services.DisponibilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DisponibilidadServiceImpl implements DisponibilidadService {

    @Autowired
    DisponibilidadRepository disponibilidadRepository;

    @Override
    public List<Disponibilidad> getAll() {
        return disponibilidadRepository.findAll();
    }

    @Override
    public List<Disponibilidad> mappear(List<Integer> ids) {
        List<Disponibilidad> disponibilidades = ids.stream().map(id-> disponibilidadRepository.findById(id).orElse(null)).collect(Collectors.toList());
        return disponibilidades;
    }

    @Override
    public void save(Disponibilidad disponibilidad){
        disponibilidadRepository.save(disponibilidad);
    }

    @Override
    public void deleteAll(){
        disponibilidadRepository.deleteAll();
    }

}