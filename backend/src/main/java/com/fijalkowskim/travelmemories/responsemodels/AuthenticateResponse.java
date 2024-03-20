package com.fijalkowskim.travelmemories.responsemodels;

import com.fijalkowskim.travelmemories.models.users.User;
import lombok.Data;

@Data
public class AuthenticateResponse {
    User user;
    String token;
}
