package com.fijalkowskim.travelmemories.models.photos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fijalkowskim.travelmemories.models.users.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Builder
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "photo_id", nullable = false)
    @JsonIgnore
    private Photo photo;

    public Like() {

    }

    public Like(Long id, User user, Photo photo) {
        this.id = id;
        this.user = user;
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return Objects.equals(id, like.id) && Objects.equals(user, like.user) && Objects.equals(photo, like.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, photo);
    }
}
