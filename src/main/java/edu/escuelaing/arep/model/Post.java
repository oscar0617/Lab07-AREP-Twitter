package edu.escuelaing.arep.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usernameId;
    private String content;

    public Post(){}

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }
    
    public Long getUsernameId() {
        return usernameId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsernameId(Long usernameId) {
        this.usernameId = usernameId;
    }
}
