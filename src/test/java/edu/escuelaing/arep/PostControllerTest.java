package edu.escuelaing.arep;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import edu.escuelaing.arep.controller.PostController;
import edu.escuelaing.arep.model.Post;
import edu.escuelaing.arep.service.PostService;

class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePostSuccess() {
        String username = "user1";
        String hilo = "Noticias";
        String content = "Este es un post de prueba";

        Post mockPost = new Post(username, content, hilo);
        when(postService.createPost(username, content, hilo)).thenReturn(mockPost);

        ResponseEntity<?> response = postController.createPost(username, hilo, content);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockPost, response.getBody());
        verify(postService, times(1)).createPost(username, content, hilo);
    }

    @Test
    void testCreatePostContentTooLong() {
        String longContent = "a".repeat(141); // Más de 140 caracteres

        ResponseEntity<?> response = postController.createPost("user1", "Noticias", longContent);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("El contenido del post no puede superar los 140 caracteres.", response.getBody());
        verify(postService, never()).createPost(anyString(), anyString(), anyString());
    }

    @Test
    void testGetAllPosts() {
        List<Post> mockPosts = Arrays.asList(new Post("user1", "Hola!", "Noticias"));
        when(postService.getAllPosts()).thenReturn(mockPosts);

        ResponseEntity<List<Post>> response = postController.getAllPosts();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockPosts, response.getBody());
        verify(postService, times(1)).getAllPosts();
    }
}
