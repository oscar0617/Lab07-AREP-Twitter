package edu.escuelaing.arep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import edu.escuelaing.arep.model.Post;
import edu.escuelaing.arep.service.PostService;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@AuthenticationPrincipal Jwt jwt, @RequestParam String nombreHilo,
            @RequestParam String content) {
        if (content.length() > 140) {
            return ResponseEntity.badRequest().body("El contenido del post no puede superar los 140 caracteres.");
        }

        String username = jwt.getClaim("username");
        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado.");
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
