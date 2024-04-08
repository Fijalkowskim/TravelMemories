package com.fijalkowskim.travelmemories.responsemodels;

import com.fijalkowskim.travelmemories.models.users.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticateResponse {
    User user;
    String token;
}
