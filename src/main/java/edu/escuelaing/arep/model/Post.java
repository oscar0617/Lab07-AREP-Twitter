package edu.escuelaing.arep.model;

import jakarta.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String content;
    private String nombreHilo;

    public Post() {}

    public Post(String username, String content, String nombreHilo) {
        this.username = username;
        this.content = content;
        this.nombreHilo = nombreHilo;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String getNombreHilo() {
        return nombreHilo;
    }
}
