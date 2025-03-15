package edu.escuelaing.arep.controller;

import edu.escuelaing.arep.model.Post;
import edu.escuelaing.arep.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestParam String username, @RequestParam String nombreHilo, @RequestParam String content) {
        if (content.length() > 140) {
            return ResponseEntity.badRequest().body("El contenido del post no puede superar los 140 caracteres.");
        }

        Post newPost = postService.createPost(username, content, nombreHilo);
        return ResponseEntity.ok(newPost);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
}
