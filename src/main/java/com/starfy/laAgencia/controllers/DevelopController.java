package com.starfy.laAgencia.controllers;

import com.starfy.laAgencia.dtos.Response;
import com.starfy.laAgencia.dtos.requests.RequestCreateBailarin;
import com.starfy.laAgencia.models.*;
import com.starfy.laAgencia.services.*;
import com.starfy.laAgencia.utils.PersonaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/develop")
public class DevelopController {

    @Autowired
    private BailarinService bailarinService;

    @Autowired
    private IdiomaService idiomaService;
    @Autowired
    private TextoService textoService;
    @Autowired
    private DisponibilidadService disponibilidadService;
    @Autowired
    private RolService rolService;
    @Autowired
    private PersonaUtils personaUtils;

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private AdminService adminService;


    @Transactional
    @PostMapping("/populate")
    public ResponseEntity<?> populate() throws NoSuchAlgorithmException {

        // ========================================
        Disponibilidad disponibilidad1 = new Disponibilidad();
        disponibilidad1.setValor("bailarin");

        Disponibilidad disponibilidad2 = new Disponibilidad();
        disponibilidad2.setValor("maestro");

        Disponibilidad disponibilidad3 = new Disponibilidad();
        disponibilidad3.setValor("coach");

        Disponibilidad disponibilidad4 = new Disponibilidad();
        disponibilidad4.setValor("taxi dancer");

        disponibilidadService.save(disponibilidad1);
        disponibilidadService.save(disponibilidad2);
        disponibilidadService.save(disponibilidad3);
        disponibilidadService.save(disponibilidad4);

        // ========================================

        Rol rol1 = new Rol();
        rol1.setValor("lider");

        Rol rol2 = new Rol();
        rol2.setValor("follower");

        Rol rol3 = new Rol();
        rol3.setValor("ambos");

        rolService.save(rol1);
        rolService.save(rol2);
        rolService.save(rol3);

        // ========================================


        Idioma idioma1 = new Idioma();
        idioma1.setNombre("español");
        idioma1.setNombreCorto("ES");

        Idioma idioma2 = new Idioma();
        idioma2.setNombre("portugues");
        idioma2.setNombreCorto("POR");

        Idioma idioma3 = new Idioma();
        idioma3.setNombre("ingles");
        idioma3.setNombreCorto("EN");

        idiomaService.save(idioma1);
        idiomaService.save(idioma2);
        idiomaService.save(idioma3);

        // =======================================

        Texto texto1 = new Texto();
        texto1.setValor("{'titulo': 'Bailarines','boton_confirmar': 'confirmar'}");
        texto1.setIdioma(idioma1);

        Texto texto2 = new Texto();
        texto2.setValor("{'titulo': 'Dançarinos','boton_confirmar': 'confirmar'}");
        texto2.setIdioma(idioma2);

        Texto texto3 = new Texto();
        texto3.setValor("{'titulo': 'Dancers','boton_confirmar': 'confirm'}");
        texto3.setIdioma(idioma3);

        textoService.save(texto1);
        textoService.save(texto2);
        textoService.save(texto3);

        idioma1.setTexto(texto1);
        idioma2.setTexto(texto2);
        idioma3.setTexto(texto3);


        idiomaService.save(idioma1);
        idiomaService.save(idioma2);
        idiomaService.save(idioma3);

        // ========================================

        RequestCreateBailarin request1 = new RequestCreateBailarin();
        request1.setNombre("zeke");
        request1.setInstagram("zeke.ig");
        request1.setTelefono("12345678");
        request1.setAltura(170L);
        request1.setPais("Argentina");
        request1.setTienePasaporte(true);
        request1.setEsDestacado(false);

        List<Idioma> idiomas = new ArrayList<>();
        idiomas.add(idioma1);

        List<Disponibilidad> disponibilidads = new ArrayList<>();
        disponibilidads.add(disponibilidad1);
        disponibilidads.add(disponibilidad2);

        List<Rol> roles = new ArrayList<>();
        roles.add(rol1);
        roles.add(rol2);

        String codigo = personaUtils.getCode();

        Bailarin bailarin1 = new Bailarin(request1, codigo, idiomas, disponibilidads, roles);


        RequestCreateBailarin request2 = new RequestCreateBailarin();
        request2.setNombre("Juan");
        request2.setInstagram("juanito_bailarin");
        request2.setTelefono("0987654321");
        request2.setAltura(180L);
        request2.setPais("México");
        request2.setTienePasaporte(true);
        request2.setEsDestacado(true);

        List<Idioma> idiomas2 = new ArrayList<>();
        idiomas.add(idioma3);

        List<Disponibilidad> disponibilidades2 = new ArrayList<>();
        disponibilidads.add(disponibilidad1);
        disponibilidads.add(disponibilidad3);

        List<Rol> roles2 = new ArrayList<>();
        roles2.add(rol1);
        roles2.add(rol2);

        String codigo2 = personaUtils.getCode();

        Bailarin bailarin2 = new Bailarin(request2, codigo2, idiomas2, disponibilidades2, roles2);


        RequestCreateBailarin request = new RequestCreateBailarin();
        request.setNombre("Ana");
        request.setInstagram("ana_bailarina");
        request.setTelefono("1234567890");
        request.setAltura(165L);
        request.setPais("España");
        request.setTienePasaporte(true);
        request.setEsDestacado(false);

        List<Idioma> idiomas3 = new ArrayList<>();
        idiomas3.add(idioma2);

        List<Disponibilidad> disponibilidades3 = new ArrayList<>();
        disponibilidads.add(disponibilidad2);

        List<Rol> roles3 = new ArrayList<>();
        roles3.add(rol1);

        String codigo3 = personaUtils.getCode();

        Bailarin bailarin3 = new Bailarin(request, codigo3, idiomas3, disponibilidades3, roles3);

        bailarinService.save(bailarin1);
        bailarinService.save(bailarin2);
        bailarinService.save(bailarin3);
//
//        Admin admin1 = new Admin("zeke", tokenService.cifrarMD5("zeke"));
//        Admin admin2 = new Admin("brian", tokenService.cifrarMD5("123"));
//        Admin admin3 = new Admin("tio", tokenService.cifrarMD5("091218"));
//
//        adminService.save(admin1);
//        adminService.save(admin2);
//        adminService.save(admin3);


        Response response = new Response("Correcto", "Se relleno la base de datos correctamente", "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?>deleteAll(){
        bailarinService.deleteAll();
        idiomaService.deleteAll();
        textoService.deleteAll();
        disponibilidadService.deleteAll();
        rolService.deleteAll();

        return new ResponseEntity<>("salio ok", HttpStatus.OK);
    }
}
