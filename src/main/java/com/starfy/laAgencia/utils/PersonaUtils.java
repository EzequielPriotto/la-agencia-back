package com.starfy.laAgencia.utils;

import com.starfy.laAgencia.models.Bailarin;
import com.starfy.laAgencia.services.BailarinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PersonaUtils {
    private final String ABECEDARIO = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    BailarinService bailarinService;

    public String getCode(){
        StringBuilder codigo;
        Bailarin bailarin = null;
        do{
            codigo = new StringBuilder(5);
            for (int i = 0; i < 5; i++) {
                int randomIndex = secureRandom.nextInt(ABECEDARIO.length());
                char randomCaracter = ABECEDARIO.charAt(randomIndex);
                codigo.append(randomCaracter);
            }
            bailarin = bailarinService.getByCodigo(codigo.toString());
        }while (bailarin != null);

        return codigo.toString();
    }


}
