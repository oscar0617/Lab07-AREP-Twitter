package edu.escuelaing.arep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.escuelaing.arep.model.Hilo;
import edu.escuelaing.arep.service.HiloService;
import jakarta.annotation.PostConstruct;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hilos")
public class HiloController {

    @Autowired
    private HiloService hiloService;

    @PostConstruct
    public void init() {
        if (hiloService.getAllHilos().isEmpty()) {
            hiloService.createHilo("Noticias");
            hiloService.createHilo("Deportes");
            hiloService.createHilo("Entretenimiento");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Hilo>> getAllHilos() {
        init();
        return ResponseEntity.ok(hiloService.getAllHilos());
    }
}
