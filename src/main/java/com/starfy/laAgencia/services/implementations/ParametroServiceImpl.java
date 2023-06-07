package com.starfy.laAgencia.services.implementations;

import com.starfy.laAgencia.exceptions.CustomException;
import com.starfy.laAgencia.repositories.ParametroRepository;
import com.starfy.laAgencia.services.ParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ParametroServiceImpl implements ParametroService {

    @Autowired
    private ParametroRepository parametroRepository;

    @Override
    public String getParametro(String clave){
        return parametroRepository.findById_Clave(clave)
                .orElseThrow(()-> new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Parametro no encontrado con la clave: " + clave))
                .getId().getValor();
    }

}
