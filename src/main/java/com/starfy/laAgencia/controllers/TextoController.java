package com.starfy.laAgencia.controllers;

import com.starfy.laAgencia.dtos.ClaveValorDTO;
import com.starfy.laAgencia.dtos.Response;
import com.starfy.laAgencia.dtos.requests.RequestCreateTexto;
import com.starfy.laAgencia.models.Disponibilidad;
import com.starfy.laAgencia.models.Idioma;
import com.starfy.laAgencia.models.Texto;
import com.starfy.laAgencia.services.ErroresService;
import com.starfy.laAgencia.services.IdiomaService;
import com.starfy.laAgencia.services.TextoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/textos")
@Api(tags = "textos", description = "Endpoints para manejar los textos")
public class TextoController {

    @Autowired
    private IdiomaService idiomaService;
    @Autowired
    private TextoService textoService;
    @Autowired
    private ErroresService erroresService;

    @ApiOperation(value = "Obtiene una lista de textos", tags = "textos", response = Disponibilidad[].class)
    @GetMapping("/")
    public ResponseEntity<?> getAllTextos(){
        List<Idioma> idiomas = idiomaService.getAll();
        List<ClaveValorDTO> textos = idiomas.stream().map(idioma -> new ClaveValorDTO(idioma.getTexto().getId(), idioma.getTexto().getValor())).collect(Collectors.toList());
        Response response = new Response("Correcto",textos,"");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Crear texto", tags = "textos")
    @PostMapping("/")
    public ResponseEntity<?> creaTexto(@RequestBody @Valid RequestCreateTexto request, BindingResult result){

        if (result.hasErrors()) {
            Response response = erroresService.obtenerErrores(result);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        Idioma idioma = idiomaService.getById(request.getIdioma());

        if(idioma == null ){
            Response response = new Response("Bad Request","","El id del idioma no es correcto");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }


        Texto newTexto = new Texto();
        newTexto.setValor(request.getTexto());
        newTexto.setIdioma(idioma);
        idioma.setTexto(newTexto);

        textoService.save(newTexto);
        idiomaService.save(idioma);


        Response response = new Response("Creado","El texto se creo correctamente","");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
