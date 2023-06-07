package com.starfy.laAgencia.controllers;


import com.starfy.laAgencia.dtos.AdminDTO;
import com.starfy.laAgencia.dtos.BailarinDTO;
import com.starfy.laAgencia.dtos.BailarinPanelDTO;
import com.starfy.laAgencia.dtos.Response;
import com.starfy.laAgencia.dtos.requests.RequestCreateBailarin;
import com.starfy.laAgencia.dtos.requests.RequestUpdateBailarin;
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
@RequestMapping("/bailarines")
@Api(tags = "bailarines", description = "Endpoints para manejar los bailarines")
public class BailarinController {

    private static final Logger logger = LoggerFactory.getLogger(BailarinController.class);

    @Autowired
    private BailarinService bailarinService;
    @Autowired
    private ErroresService erroresService;
    @Autowired
    private IdiomaService idiomaService;
    @Autowired
    private DisponibilidadService disponibilidadService;
    @Autowired
    private RolService rolService;
    @Autowired
    private FotoService fotoService;
    @Autowired
    private PersonaUtils personaUtils;
    @Autowired
    private JwtTokenService tokenService;

    @ApiOperation(value = "Obtiene una lista de bailarines", tags = "bailarines", notes = " El endpoint devuelve la informacion completa del usuario solo si estas logeado como admin y se envia el parametro infoCompleta como true", response = Bailarin[].class)
    @GetMapping("/")
    public ResponseEntity<?> getAllBailarines(Boolean infoCompleta, HttpServletRequest request) {
        Response response = new Response();
        AdminDTO admin = null;

        try {
            admin = tokenService.getInfoFromJWT(tokenService.obtenerJwt(request));
        } catch (Exception e) {
           logger.info("No esta logueado");
        }


        if (admin != null && infoCompleta) {
            List<BailarinPanelDTO> bailarines = bailarinService.getAll().stream().map(BailarinPanelDTO::new).collect(Collectors.toList());
            response.setData(bailarines);
        } else {
            List<BailarinDTO> bailarines = bailarinService.getAll().stream().map(BailarinDTO::new).collect(Collectors.toList());
            response.setData(bailarines);
        }

        response.setStatus("Correcto");
        response.setError("");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtiene una lista de los bailarines destacados", tags = "bailarines", response = Bailarin[].class)
    @GetMapping("/destacados")
    public ResponseEntity<?> getBailarinesDestacados() {
        List<BailarinDTO> bailarines = bailarinService.getDestacados().stream().map(BailarinDTO::new).collect(Collectors.toList());
        Response response = new Response("Correcto", bailarines, "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtiene un bailarin mediante su id", tags = "bailarines", response = BailarinDTO.class)
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Bailarin bailarin = bailarinService.getById(id);

        if (bailarin == null) {
            Response response = new Response("Bad Request", "Id incorrecto", "");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        BailarinDTO bailarinDTO = new BailarinDTO(bailarin);

        Response response = new Response("Correcto", bailarinDTO, "");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtiene un bailarin mediante su codigo", tags = "bailarines", response = BailarinDTO.class)
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<?> getByCodigo(@PathVariable String codigo) {
        Bailarin bailarin = bailarinService.getByCodigo(codigo);
        if (bailarin == null) {
            Response response = new Response("Bad Request", "Codigo incorrecto", "");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        BailarinDTO bailarinDTO = new BailarinDTO(bailarin);

        Response response = new Response("Correcto", bailarinDTO, "");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value = "Crea un bailarin", tags = "bailarines")
    @PostMapping("/")
    public ResponseEntity<?> createBailarin(@RequestBody @Valid RequestCreateBailarin request, BindingResult result) {

        if (result.hasErrors()) {
            Response response = erroresService.obtenerErrores(result);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        List<Disponibilidad> disponibilidads = disponibilidadService.mappear(request.getDisponibilidades());

        if(disponibilidads.size() < 1){
            Response response = new Response("Error", "", "Disponibilidades erroneas");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        List<Idioma> idiomas = idiomaService.mappear(request.getIdiomas());

        if(idiomas.size() < 1){
            Response response = new Response("Error", "", "Idiomas erroneos");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        List<Rol> roles = rolService.mappear(request.getRoles());

        if(roles.size() < 1){
            Response response = new Response("Error", "", "Roles erroneos");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String codigo = personaUtils.getCode();

        Bailarin bailarin = new Bailarin(request, codigo, idiomas, disponibilidads, roles);
        bailarinService.save(bailarin);
       try{
           Response response = new Response("Creado", new ResponseCreate(bailarin), "");
           return new ResponseEntity<>(response, HttpStatus.CREATED);
       }catch (Exception e){
           Response response = new Response("Error interno", "El bailarin no se pudo crear", "");
           return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @Transactional
    @ApiOperation(value = "Modifica un bailarin", tags = "bailarines")
    @PatchMapping("/")
    public ResponseEntity<?> updateBailarin(@RequestBody @Valid RequestUpdateBailarin request, BindingResult result) {

        if (result.hasErrors()) {
            Response response = erroresService.obtenerErrores(result);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Bailarin bailarin = bailarinService.getById(request.getId());

        if (bailarin == null) {
            Response response = new Response("Bad Request", "", "Id incorrecto");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        List<Idioma> idiomas = null;

        if (request.getIdiomas() != null) {
            idiomas = idiomaService.mappear(request.getIdiomas());
        }

        List<Disponibilidad> disponibilidads = null;

        if (request.getDisponibilidades() != null) {
            disponibilidads = disponibilidadService.mappear(request.getDisponibilidades());
        }

        List<Rol> roles = null;

        if (request.getRoles() != null) {
            roles = rolService.mappear(request.getRoles());
        }

        bailarin.setTienePasaporte(request.isTienePasaporte());
        bailarin.setAltura(request.getAltura());
        bailarin.setEdad(request.getEdad());
        bailarin.setInstagram(request.getInstagram());
        bailarin.setNombre(request.getNombre());
        bailarin.setTelefono(request.getTelefono());
        bailarin.setPais(request.getPais());
        bailarin.setIdiomas(idiomas);
        bailarin.setDisponibilidades(disponibilidads);
        bailarin.setRoles(roles);
        bailarin.setEsDestacado(request.isEsDestacado());
        bailarinService.save(bailarin);

        Response response = new Response("Actualizado", "El bailarin se actualizo correctamente", "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value = "Destaca un bailarin", tags = "bailarines")
    @PatchMapping("/destacar")
    public ResponseEntity<?> destacarBailarin(@RequestParam Integer id) {

        if (id == null) {
            Response response = new Response("Bad Request", "", "Id no puede estar vacio");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Bailarin bailarin = bailarinService.getById(id);

        if (bailarin == null) {
            Response response = new Response("Bad Request", "", "Id incorrecto");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        bailarin.setEsDestacado(true);
        bailarinService.save(bailarin);

        Response response = new Response("Actualizado", "El bailarin se destaco correctamente", "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtiene un foto de un bailarin", tags = "bailarines", notes = " El endpoint devuelve la foto del bailarin que se indique", response = Bailarin[].class)
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
    @ApiOperation(value = "Cargar una foto a un bailarin", tags = "bailarines")
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

        Bailarin bailarin = bailarinService.getById(id);

        if(bailarin == null){
            Response response = new Response("Bad Request", "", "Bailarin no encontrado");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        byte[] source = file.getBytes();

        Foto foto = new Foto();
        foto.setPersona(bailarin);
        foto.setSource(source);

        fotoService.save(foto);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        url = url  + "/bailarines/getFoto/"+ foto.getId();
        foto.setUrl(url);
        fotoService.save(foto);

        bailarin.addFoto(foto);

        try {
            bailarinService.save(bailarin);
            logger.debug("Se cargo la imagen del bailarin con el id: {}", bailarin.getId());
        }catch (Exception e){
            logger.error("Fallo el proceso de subida de imagen, error: {}", e.toString());
            Response response = new Response("Internal server error", "", "Fallo el proceso de guardado de imagen");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Response response = new Response("Correct", new ResponseUploadPhoto(foto.getId()), "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value = "Eliminar una foto a un bailarin", tags = "bailarines")
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
            Bailarin bailarin = bailarinService.getById(persona.getId());
            bailarin.removeFoto(foto);
            bailarinService.save(bailarin);
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
    @ApiOperation(value = "Eliminar un bailarin", tags = "bailarines")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteBailarin(@RequestParam Integer id) {

        if (id == null || id < 0) {
            Response response = new Response("Bad request", "", "Id invalido");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Bailarin bailarin = bailarinService.getById(id);

        if (bailarin == null) {
            Response response = new Response("Bad Request", "", "Id incorrecto");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        bailarinService.delete(bailarin);

        Response response = new Response("Borrado", "El bailarin se borro correctamente", "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
