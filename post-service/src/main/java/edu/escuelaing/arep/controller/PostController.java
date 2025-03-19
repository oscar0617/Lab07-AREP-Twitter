package edu.escuelaing.arep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.escuelaing.arep.model.Post;
import edu.escuelaing.arep.service.PostService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        if (post.getContent().length() > 140) {
            return ResponseEntity.badRequest().body("El contenido del post no puede superar los 140 caracteres.");
        }

        if (post.getUsername() == null || post.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado.");
        }

        Post newPost = postService.createPost(post.getUsername(), post.getContent(), post.getNombreHilo());
        return ResponseEntity.ok(newPost);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
}
