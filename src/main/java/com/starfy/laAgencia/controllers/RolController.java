package com.starfy.laAgencia.controllers;

import com.starfy.laAgencia.dtos.ClaveValorDTO;
import com.starfy.laAgencia.dtos.Response;
import com.starfy.laAgencia.models.Disponibilidad;
import com.starfy.laAgencia.models.Rol;
import com.starfy.laAgencia.services.RolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
@Api(tags = "roles", description = "Endpoints para manejar los roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @ApiOperation(value = "Obtiene una lista de roles", tags = "roles", response = Disponibilidad[].class)
    @GetMapping("/")
    public ResponseEntity<?> getAllRoles(){
        List<ClaveValorDTO> roles = rolService.getAll().stream().map(rol -> new ClaveValorDTO(rol.getId(), rol.getValor())).collect(Collectors.toList());
        Response response = new Response("Correcto",roles,"");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @ApiOperation(value = "Crear rol", tags = "roles")
    @PostMapping("/")
    public ResponseEntity<?> crearRol(@RequestParam String nombre){

        if(nombre == null || nombre.isEmpty()){
            Response response = new Response("Bad Request","","El nombre del rol no tiene que estar vacio");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        Rol rol = new Rol();
        rol.setValor(nombre);
        rolService.save(rol);

        Response response = new Response("Creado","El rol se creo correctamente","");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }





}
