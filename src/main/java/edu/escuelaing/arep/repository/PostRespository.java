package edu.escuelaing.arep.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.escuelaing.arep.model.Post;

public interface PostRespository extends CrudRepository<Post, Long> {
    Optional<Post> findById(Long id);
    List<Post> findAll();
}
