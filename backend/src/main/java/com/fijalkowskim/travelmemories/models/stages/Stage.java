package com.fijalkowskim.travelmemories.models.stages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fijalkowskim.travelmemories.models.photos.Photo;
import com.fijalkowskim.travelmemories.models.travels.Travel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "stages")
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "stage_date")
    private Date stageDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @JsonIgnore
    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos;

    @OneToOne
    @JoinColumn(name = "thumbnail_id", referencedColumnName = "id")
    private Photo thumbnail;

}
