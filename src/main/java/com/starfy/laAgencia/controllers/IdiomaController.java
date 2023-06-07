package com.starfy.laAgencia.controllers;

import com.starfy.laAgencia.dtos.IdiomaDTO;
import com.starfy.laAgencia.dtos.Response;
import com.starfy.laAgencia.models.Disponibilidad;
import com.starfy.laAgencia.models.Idioma;
import com.starfy.laAgencia.services.IdiomaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/idiomas")
@Api(tags = "idiomas", description = "Endpoints para manejar los idiomas")
public class IdiomaController {

    @Autowired
    private IdiomaService idiomaService;


    @ApiOperation(value = "Obtiene una lista de idiomas", tags = "idiomas", response = Disponibilidad[].class)
    @GetMapping("/")
    public ResponseEntity<?> getAllIdiomas(){
        List<IdiomaDTO> idiomas = idiomaService.getAll().stream().map(IdiomaDTO::new).collect(Collectors.toList());
        Response response = new Response("Correcto",idiomas,"");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Crear idioma", tags = "idiomas")
    @PostMapping("/")
    public ResponseEntity<?> crearIdioma(@RequestParam String nombre, @RequestParam String nombreCorto){

        if(nombre == null || nombre.isEmpty()){
            Response response = new Response("Bad Request","","El nombre del idioma no tiene que estar vacio");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }

        if(nombreCorto == null || nombreCorto.isEmpty()){
            Response response = new Response("Bad Request","","El nombre corto del idioma no tiene que estar vacio");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }

        Idioma idioma = new Idioma();
        idioma.setNombre(nombre);
        idioma.setNombreCorto(nombreCorto);
        idiomaService.save(idioma);

        Response response = new Response("Creado","El idioma se creo correctamente","");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
