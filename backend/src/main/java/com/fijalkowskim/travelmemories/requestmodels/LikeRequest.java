package com.fijalkowskim.travelmemories.requestmodels;

import lombok.Data;

@Data
public class LikeRequest {
    private long userId;
    private long photoId;
}
