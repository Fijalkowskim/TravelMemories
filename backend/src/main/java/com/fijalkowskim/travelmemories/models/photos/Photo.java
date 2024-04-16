package com.fijalkowskim.travelmemories.models.photos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fijalkowskim.travelmemories.models.stages.Stage;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "photos")
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
    private Date date;

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
    private Set<Like> likes = new HashSet<>();

    public Photo(Long id, String description, byte[] photoData, Long privacy, Date date, Double latitude, Double longitude, String locationName, Stage stage, Set<Like> likes) {
        this.id = id;
        this.description = description;
        this.photoData = photoData;
        this.privacy = privacy;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
        this.stage = stage;
        this.likes = likes;
    }

    public Photo() {
    }
}
