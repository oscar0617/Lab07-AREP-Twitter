package edu.escuelaing.arep.controller;

import edu.escuelaing.arep.model.Hilo;
import edu.escuelaing.arep.service.HiloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import java.util.List;

@RestController
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
        return ResponseEntity.ok(hiloService.getAllHilos());
    }
}
