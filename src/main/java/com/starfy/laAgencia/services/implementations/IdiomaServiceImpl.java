package com.starfy.laAgencia.services.implementations;

import com.starfy.laAgencia.models.Idioma;
import com.starfy.laAgencia.repositories.IdiomaRepository;
import com.starfy.laAgencia.services.IdiomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class IdiomaServiceImpl implements IdiomaService {

    @Autowired
    IdiomaRepository idiomaRepository;

    @Override
    public List<Idioma> mappear(List<Integer> ids){
        List<Idioma> idiomas = ids.stream().map(id-> idiomaRepository.findById(id).orElse(null)).collect(Collectors.toList());
        return idiomas;
    }

    @Override
    public Idioma getById(Integer id) {
        return idiomaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Idioma> getAll() {
        return idiomaRepository.findAll();
    }

    @Override
    public void save(Idioma idioma){
        idiomaRepository.save(idioma);
    }
    @Transactional
    @Override
    public void deleteAll(){
        idiomaRepository.deleteAll();
    }
}