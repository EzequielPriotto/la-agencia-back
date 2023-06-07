package com.starfy.laAgencia.services.implementations;

import com.starfy.laAgencia.models.Maestro;
import com.starfy.laAgencia.repositories.MaestroRepository;
import com.starfy.laAgencia.services.MaestroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaestroServiceImpl implements MaestroService {

    @Autowired
    MaestroRepository maestroRepository;

    @Override
    public List<Maestro> getAll(){
        return maestroRepository.findAll();
    }

    @Override
    public Maestro getById(Integer id){
        return maestroRepository.findById(id).orElse(null);
    }

    @Override
    public Maestro getByCodigo(String codigo){
        return maestroRepository.findByCodigo(codigo).orElse(null);
    }

    @Transactional
    @Override
    public void save(Maestro maestro) {
            maestroRepository.save(maestro);
    }

    @Transactional
    @Override
    public void delete(Maestro maestro) {
        maestroRepository.delete(maestro);
    }

    @Transactional
    @Override
    public void deleteAll() {
        maestroRepository.deleteAll();
    }


}
