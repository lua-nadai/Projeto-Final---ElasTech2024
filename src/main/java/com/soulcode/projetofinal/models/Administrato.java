package com.soulcode.projetofinal.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity

public class Administrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String email;

    @Column(length = 255, nullable = false)
    @Size(min = 8)
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "requestId")
    private Set<SupportRequest> requests;

    public Administrato(String name, String email, String password, Set<SupportRequest> requests) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.requests = requests;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<SupportRequest> getRequests() {
        return requests;
    }

    public void setRequests(Set<SupportRequest> requests) {
        this.requests = requests;
    }
}
