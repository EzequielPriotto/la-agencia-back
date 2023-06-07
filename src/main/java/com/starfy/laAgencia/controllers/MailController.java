package com.starfy.laAgencia.controllers;


import com.starfy.laAgencia.dtos.Response;
import com.starfy.laAgencia.dtos.requests.RequestMailContacto;
import com.starfy.laAgencia.services.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@Api(tags = "mail", description = "Endpoints para manejar envio de mails")
public class MailController {

    @Autowired
    private ErroresService erroresService;

    @Autowired
    private MailService mailService;


    @Transactional
    @ApiOperation(value = "Envia un mail a la casilla de la agencia", tags = "mail")
    @PostMapping("/contacto")
    public ResponseEntity<?> createBailarin(@RequestBody @Valid RequestMailContacto request, BindingResult result) {

        if (result.hasErrors()) {
            Response response = erroresService.obtenerErrores(result);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        mailService.enviarMailContacto(request);

        Response response = new Response("Correcto", "El mail se envio correctamente", "");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
