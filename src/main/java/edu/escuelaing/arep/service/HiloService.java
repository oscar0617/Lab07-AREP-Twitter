package edu.escuelaing.arep.service;

import edu.escuelaing.arep.model.Hilo;
import edu.escuelaing.arep.model.Post;
import edu.escuelaing.arep.repository.HiloRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HiloService {
    @Autowired
    private HiloRespository hiloRepository;

    public Hilo createHilo(String nombre) {
        return hiloRepository.save(new Hilo(nombre));
    }

    public Optional<Hilo> getHiloById(Long id) {
        return hiloRepository.findById(id);
    }

    public List<Hilo> getAllHilos() {
        return hiloRepository.findAll();
    }

    public void addPostToHilo(Long hiloId, Post post) {
        Optional<Hilo> optionalHilo = hiloRepository.findById(hiloId);
        
        if (optionalHilo.isPresent()) {
            Hilo hilo = optionalHilo.get();
            hilo.addPost(post);
            hiloRepository.save(hilo);
        } else {
            throw new RuntimeException("Hilo con ID " + hiloId + " no encontrado.");
        }
    }
}
