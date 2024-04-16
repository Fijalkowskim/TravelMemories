package com.fijalkowskim.travelmemories.requestmodels;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeRequest {
    private long userId;
    private long photoId;
}
