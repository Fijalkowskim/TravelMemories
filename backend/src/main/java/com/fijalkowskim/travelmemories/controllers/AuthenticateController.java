package com.fijalkowskim.travelmemories.controllers;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fijalkowskim.travelmemories.models.users.User;
import com.fijalkowskim.travelmemories.services.AuthenticateService;

@Controller
@RequestMapping( value = "/api/public/authenticate")
@CrossOrigin("http://localhost:3000")
public class AuthenticateController {
    private final AuthenticateService userService;
    @Autowired
    public AuthenticateController(AuthenticateService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> checkPassword(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password) throws CustomHTTPException {

        User user = userService.authenticate(email,password);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "oldPassword") String oldPassword,
            @RequestParam(name = "newPassword") String newPassword) throws CustomHTTPException{
        userService.changePassword(email,oldPassword, newPassword);
        return ResponseEntity.ok("Password has been changed.");
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteUser(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password) throws CustomHTTPException{
        userService.deleteUser(email,password);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @PutMapping("")
    public ResponseEntity<User> createNewUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password) throws CustomHTTPException{
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(email,password));
    }

}
