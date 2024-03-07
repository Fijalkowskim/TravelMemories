package com.fijalkowskim.travelmemories.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "photo_data")
    private byte[] photoData;

    @Column(name = "privacy")
    private Long privacy;

    @Column(name = "photo_date")
    private String photoDate;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "location_name")
    private String locationName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

    @JsonIgnore
    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Likes> likes = new HashSet<>();
}