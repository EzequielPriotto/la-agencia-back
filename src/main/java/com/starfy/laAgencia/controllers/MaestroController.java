package com.starfy.laAgencia.controllers;


import com.starfy.laAgencia.dtos.*;
import com.starfy.laAgencia.dtos.requests.RequestCreateBailarin;
import com.starfy.laAgencia.dtos.requests.RequestCreateMaestro;
import com.starfy.laAgencia.dtos.requests.RequestUpdateBailarin;
import com.starfy.laAgencia.dtos.requests.RequestUpdateMaestro;
import com.starfy.laAgencia.dtos.responses.ResponseCreate;
import com.starfy.laAgencia.dtos.responses.ResponseUploadPhoto;
import com.starfy.laAgencia.models.*;
import com.starfy.laAgencia.services.*;
import com.starfy.laAgencia.utils.PersonaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/maestros")
@Api(tags = "maestros", description = "Endpoints para manejar los maestros")
public class MaestroController {

    private static final Logger logger = LoggerFactory.getLogger(MaestroController.class);

    @Autowired
    private MaestroService maestroService;
    @Autowired
    private ErroresService erroresService;

    @Autowired
    private FotoService fotoService;
    @Autowired
    private PersonaUtils personaUtils;
    @Autowired
    private JwtTokenService tokenService;

    @ApiOperation(value = "Obtiene una lista de maestros", tags = "maestros", notes = " El endpoint devuelve la informacion completa del usuario solo si estas logeado como admin y se envia el parametro infoCompleta como true", response = Maestro[].class)
    @GetMapping("/")
    public ResponseEntity<?> getAllMaestros(Boolean infoCompleta, HttpServletRequest request) {
        Response response = new Response();
        AdminDTO admin = null;

        try {
            admin = tokenService.getInfoFromJWT(tokenService.obtenerJwt(request));
        } catch (Exception e) {
            System.out.println(e.toString());
        }


        if (admin != null && infoCompleta) {
            List<MaestroPanelDTO> maestros = maestroService.getAll().stream().map(MaestroPanelDTO::new).collect(Collectors.toList());
            response.setData(maestros);
        } else {
            List<MaestroDTO> maestros = maestroService.getAll().stream().map(MaestroDTO::new).collect(Collectors.toList());
            response.setData(maestros);
        }

        response.setStatus("Correcto");
        response.setError("");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtiene un maestro mediante su id", tags = "maestros", response = MaestroDTO.class)
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Maestro maestro = maestroService.getById(id);

        if (maestro == null) {
            Response response = new Response("Bad Request", "Id incorrecto", "");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        MaestroDTO maestroDTO = new MaestroDTO(maestro);
        Response response = new Response("Correcto", maestroDTO, "");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtiene un maestro mediante su codigo", tags = "maestros", response = MaestroDTO.class)
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<?> getByCodigo(@PathVariable String codigo) {
        Maestro maestro = maestroService.getByCodigo(codigo);
        if (maestro == null) {
            Response response = new Response("Bad Request", "Codigo incorrecto", "");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        MaestroDTO maestroDTO = new MaestroDTO(maestro);

        Response response = new Response("Correcto", maestroDTO, "");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value = "Crea un maestro", tags = "maestros")
    @PostMapping("/")
    public ResponseEntity<?> createMaestro(@RequestBody @Valid RequestCreateMaestro request, BindingResult result) {

        if (result.hasErrors()) {
            Response response = erroresService.obtenerErrores(result);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String codigo = personaUtils.getCode();

        Maestro maestro = new Maestro(request, codigo);
        maestroService.save(maestro);
       try{
           Response response = new Response("Creado", new ResponseCreate(maestro), "");
           return new ResponseEntity<>(response, HttpStatus.CREATED);
       }catch (Exception e){
           Response response = new Response("Error interno", "El maestro no se pudo crear", "");
           return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @Transactional
    @ApiOperation(value = "Modifica un maestro", tags = "maestros")
    @PatchMapping("/")
    public ResponseEntity<?> updateMaestro(@RequestBody @Valid RequestUpdateMaestro request, BindingResult result) {

        if (result.hasErrors()) {
            Response response = erroresService.obtenerErrores(result);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Maestro maestro = maestroService.getById(request.getId());

        if (maestro == null) {
            Response response = new Response("Bad Request", "", "Id incorrecto");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        maestro.setInstagram(request.getInstagram());
        maestro.setNombre(request.getNombre());
        maestro.setTelefono(request.getTelefono());

        maestroService.save(maestro);

        Response response = new Response("Actualizado", "El maestro se actualizo correctamente", "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
   
    @ApiOperation(value = "Obtiene un foto de un maestro", tags = "maestros", notes = " El endpoint devuelve la foto del maestro que se indique", response = Bailarin[].class)
    @GetMapping("/getFoto/{idFoto}")
    public void getImagen(@PathVariable Integer idFoto, HttpServletResponse response) throws IOException, SQLException {
        Foto foto = fotoService.getById(idFoto);

        if (foto == null) {
            foto = fotoService.getById(1);
        }

        byte[] source =  foto.getSource();

        try {
            response.setContentType("image/jpeg");
            response.setHeader("Cache-Control", "public, max-age=3600");
            InputStream is = new ByteArrayInputStream(source);
            IOUtils.copy(is, response.getOutputStream());
            response.setStatus(HttpServletResponse.SC_OK);
            response.flushBuffer();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocurrio un error");
        }
    }

    @Transactional
    @ApiOperation(value = "Cargar una foto a un maestro", tags = "maestros")
    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestBody MultipartFile file, @RequestParam Integer id, HttpServletRequest request) throws IOException, SQLException {

        if(id == null || id < 0){
            Response response = new Response("Bad Request", "", "Id incorrecto");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if(file == null || file.isEmpty() || file.getSize() == 0){
            Response response = new Response("Bad Request", "", "Imagen no valida");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Maestro maestro = maestroService.getById(id);

        if(maestro == null){
            Response response = new Response("Bad Request", "", "Bailarin no encontrado");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        byte[] source = file.getBytes();

        Foto foto = new Foto();
        foto.setPersona(maestro);
        foto.setSource(source);

        fotoService.save(foto);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        url = url  + "/maestros/getFoto/"+ foto.getId();
        foto.setUrl(url);
        fotoService.save(foto);

        maestro.addFoto(foto);

        try {
            maestroService.save(maestro);
            logger.debug("Se cargo la imagen del maestro con el id: {}", maestro.getId());
        }catch (Exception e){
            logger.error("Fallo el proceso de subida de imagen, error: {}", e.toString());
            Response response = new Response("Internal server error", "", "Fallo el proceso de guardado de imagen");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Response response = new Response("Correct", new ResponseUploadPhoto(foto.getId()), "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value = "Eliminar una foto a un maestro", tags = "maestros")
    @DeleteMapping("/deleteImage")
    public ResponseEntity<?> deteleImage(@RequestParam Integer id) throws IOException, SQLException {

        if(id == null || id < 0){
            Response response = new Response("Bad Request", "", "Id incorrecto");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Foto foto = fotoService.getById(id);

        if(foto == null){
            Response response = new Response("Bad Request", "", "Imagen no encontrado");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            Persona persona = foto.getPersona();
            Maestro maestro = maestroService.getById(persona.getId());
            maestro.removeFoto(foto);
            maestroService.save(maestro);
            fotoService.delete(foto);
            logger.debug("Se elimino la imagen del imagen con el id: {}", id);
        }catch (Exception e){
            logger.error("Fallo el proceso de borrado de imagen, error: {}", e.toString());
            Response response = new Response("Internal server error", "", "Fallo el proceso de borrado de imagen");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Response response = new Response("Correct", "Se borro correctamente la imagen", "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Transactional
    @ApiOperation(value = "Eliminar un maestro", tags = "maestros")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteMaestro(@RequestParam Integer id) {

        if (id == null || id < 0) {
            Response response = new Response("Bad request", "", "Id invalido");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Maestro maestro = maestroService.getById(id);

        if (maestro == null) {
            Response response = new Response("Bad Request", "", "Id incorrecto");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        maestroService.delete(maestro);

        Response response = new Response("Borrado", "El maestro se borro correctamente", "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
