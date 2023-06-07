package com.starfy.laAgencia.services.implementations;

import com.starfy.laAgencia.models.Bailarin;
import com.starfy.laAgencia.repositories.BailarinRepository;
import com.starfy.laAgencia.services.BailarinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BailarinServiceImpl implements BailarinService {

    @Autowired
    BailarinRepository bailarinRepository;

    @Override
    public List<Bailarin> getAll(){
        return bailarinRepository.findAll();
    }

    @Override
    public List<Bailarin> getDestacados(){
        return bailarinRepository.findByEsDestacado(true);
    }

    @Override
    public Bailarin getById(Integer id){
        return bailarinRepository.findById(id).orElse(null);
    }

    @Override
    public Bailarin getByCodigo(String codigo){
        return bailarinRepository.findByCodigo(codigo).orElse(null);
    }

    @Transactional
    @Override
    public void save(Bailarin bailarin) {
            bailarinRepository.save(bailarin);
    }

    @Transactional
    @Override
    public void delete(Bailarin bailarin) {
        bailarinRepository.delete(bailarin);
    }

    @Transactional
    @Override
    public void deleteAll() {
        bailarinRepository.deleteAll();
    }


}
