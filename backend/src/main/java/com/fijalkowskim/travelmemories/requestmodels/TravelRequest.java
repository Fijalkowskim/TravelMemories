package com.fijalkowskim.travelmemories.requestmodels;


import lombok.Data;

import java.util.Date;


@Data
public class TravelRequest {
    private long userId;
    private Date travelDate;
    private String locationName;
    private Double latitude;
    private Double longitude;
    private String description;
}
