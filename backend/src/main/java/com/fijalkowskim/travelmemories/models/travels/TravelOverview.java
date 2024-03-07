package com.fijalkowskim.travelmemories.models.travels;

import lombok.Data;

import java.util.Date;

@Data
public class TravelOverview {
    private long travelId;
    private Date travelDate;
    private String locationName;
    private byte[] thumbnailData;

    public TravelOverview(Travel travel) {
        this.travelId = travel.getId();
        this.travelDate = travel.getTravelDate();
        this.locationName = travel.getLocationName();
        if (travel.getThumbnail() != null) {
            this.thumbnailData = travel.getThumbnail().getPhotoData();
        }
    }
}
