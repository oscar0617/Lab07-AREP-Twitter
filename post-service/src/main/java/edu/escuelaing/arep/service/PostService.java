package edu.escuelaing.arep.service;

import edu.escuelaing.arep.model.Post;
import edu.escuelaing.arep.repository.PostRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRespository postRepository;

    public Post createPost(String username, String content, String nombreHilo) {
        if (content.length() > 140) {
            throw new IllegalArgumentException("El post no puede tener más de 140 caracteres");
        }

        Post post = new Post(username, content, nombreHilo);
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
