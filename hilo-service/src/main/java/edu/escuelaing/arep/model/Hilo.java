package edu.escuelaing.arep.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Hilo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ElementCollection
    private List<Long> postIds = new ArrayList<>();

    public Hilo() {}

    public Hilo(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Long> getPostIds() {
        return postIds;
    }

    public void addPost(Long postId) {
        postIds.add(postId);
    }
}
