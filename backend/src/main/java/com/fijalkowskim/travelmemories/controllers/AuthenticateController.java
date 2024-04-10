package com.fijalkowskim.travelmemories.controllers;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.responsemodels.AuthenticateResponse;
import com.fijalkowskim.travelmemories.services.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fijalkowskim.travelmemories.models.users.User;
import com.fijalkowskim.travelmemories.services.UserService;

@RestController
@RequestMapping( value = "/api/public/authenticate")
@CrossOrigin("http://localhost:3000")
public class AuthenticateController {
    private final AuthenticateService authenticateService;
    @Autowired
    public AuthenticateController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponse> login(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password) throws CustomHTTPException {

        return ResponseEntity.ok().body(authenticateService.login(email,password));
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticateResponse> register(
            @RequestParam("email") String email,
            @RequestParam("password") String password) throws CustomHTTPException{
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticateService.register(email,password));
    }

}
