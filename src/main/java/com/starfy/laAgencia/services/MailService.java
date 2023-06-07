package com.starfy.laAgencia.services;

import com.starfy.laAgencia.dtos.requests.RequestMailContacto;

public interface MailService {

    void enviarMailContacto(RequestMailContacto request);
}
