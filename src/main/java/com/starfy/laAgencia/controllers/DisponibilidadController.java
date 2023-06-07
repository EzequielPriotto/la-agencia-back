package com.starfy.laAgencia.controllers;

import com.starfy.laAgencia.dtos.ClaveValorDTO;
import com.starfy.laAgencia.dtos.Response;
import com.starfy.laAgencia.models.Disponibilidad;
import com.starfy.laAgencia.services.DisponibilidadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/disponibilidades")
@Api(tags = "disponibilidades", description = "Endpoints para manejar los disponibilidades")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadService disponibilidadService;

    @ApiOperation(value = "Obtiene una lista de disponibilidades", tags = "disponibilidades", response = Disponibilidad[].class)
    @GetMapping("/")
    public ResponseEntity<?> getAllDisponibilidades(){
        List<ClaveValorDTO> disponibilidades = disponibilidadService.getAll().stream().map(disponibilidad -> new ClaveValorDTO(disponibilidad.getId(), disponibilidad.getValor())).collect(Collectors.toList());
        Response response = new Response("Correcto",disponibilidades,"");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @ApiOperation(value = "Crear disponibilidad", tags = "disponibilidades")
    @PostMapping("/")
    public ResponseEntity<?> crearDisponibilidad(@RequestParam String nombre){

        if(nombre == null || nombre.isEmpty()){
            Response response = new Response("Bad Request","","El nombre de la disponibilidad no tiene que estar vacio");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        Disponibilidad disponibilidad = new Disponibilidad();
        disponibilidad.setValor(nombre);
        disponibilidadService.save(disponibilidad);

        Response response = new Response("Creado","La disponibilidad se creo correctamente","");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }





}
