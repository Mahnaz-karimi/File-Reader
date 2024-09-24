package com.jetbrains.intellij.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "app_user")  // Change the table name here
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This defines the primary key and sets it to auto-increment.
    private Long id;

    private String username;
    private String email;
    private String password;

    // Constructors, getters, setters, etc.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}