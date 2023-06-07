package com.starfy.laAgencia.services.implementations;

import com.starfy.laAgencia.models.Foto;
import com.starfy.laAgencia.repositories.FotoRepository;
import com.starfy.laAgencia.services.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FotoServiceImpl implements FotoService {

    @Autowired
    FotoRepository fotoRepository;

    @Override
    public Foto getById(Integer id){
        return fotoRepository.findById(id).orElse(null);
    }

    @Override
    public Foto getByBailarin(Integer id){
        return fotoRepository.findByPersona_Id(id).orElse(null);
    }

    @Override
    public void save(Foto foto){
        fotoRepository.save(foto);
    }
    @Transactional
    @Override
    public void delete(Foto foto){
        fotoRepository.delete(foto);
    }
}
