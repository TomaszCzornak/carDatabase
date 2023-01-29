package com.tomek.cardatabase.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;

    public UserEntity() {
    }

    public UserEntity(String username, String password,
                      String role) {
        super();
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
