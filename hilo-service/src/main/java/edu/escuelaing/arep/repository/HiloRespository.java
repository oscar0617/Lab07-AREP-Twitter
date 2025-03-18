package edu.escuelaing.arep.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.escuelaing.arep.model.Hilo;

public interface HiloRespository extends CrudRepository<Hilo, Long> {
    Optional<Hilo> findById(Long id);
    List<Hilo> findAll();
}
