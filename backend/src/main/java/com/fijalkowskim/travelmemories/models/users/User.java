package com.fijalkowskim.travelmemories.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fijalkowskim.travelmemories.models.travels.Travel;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
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

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public User(Long id, String email, String passwordHash, Set<Travel> travel, UserRole role) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.travel = travel;
        this.role = role;
    }

    public User() {
    }

    public User(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = UserRole.USER;
        this.travel = new HashSet<>();
        this.id = 0L;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
