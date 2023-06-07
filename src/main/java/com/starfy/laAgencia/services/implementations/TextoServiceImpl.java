package com.starfy.laAgencia.services.implementations;

import com.starfy.laAgencia.models.Texto;
import com.starfy.laAgencia.repositories.TextoRepository;
import com.starfy.laAgencia.services.TextoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextoServiceImpl implements TextoService {

    @Autowired
    TextoRepository textoRepository;

    @Override
    public Texto getById(Integer id){
        return textoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Texto> getAll(){
        return textoRepository.findAll();
    }

    @Override
    public void save(Texto texto){
        textoRepository.save(texto);
    }
    @Override
    public void deleteAll(){
        textoRepository.deleteAll();
    }

}
