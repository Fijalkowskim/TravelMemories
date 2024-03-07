package com.fijalkowskim.travelmemories.requestmodels;

import lombok.Data;

import java.util.Date;

@Data
public class PhotoRequest {
    private long stageId;
    private Date date;
    private String locationName;
    private Double latitude;
    private Double longitude;
    private String description;
}
