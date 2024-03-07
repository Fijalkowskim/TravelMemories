package com.fijalkowskim.travelmemories.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fijalkowskim.travelmemories.models.travels.Travel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column( name = "password_hash")
    private String passwordHash;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Travel> travel = new HashSet<>();
}
