package com.fijalkowskim.travelmemories.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fijalkowskim.travelmemories.models.User;
import com.fijalkowskim.travelmemories.services.UserService;

@Controller
@RequestMapping( value = "/api/user")
@CrossOrigin("http://localhost:3000")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> checkPassword(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password) throws Exception{

        User user = userService.authenticate(email,password);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "oldPassword") String oldPassword,
            @RequestParam(name = "newPassword") String newPassword) throws Exception{
        userService.changePassword(email,oldPassword, newPassword);
        return ResponseEntity.ok("Password has been changed.");
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteUser(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password) throws Exception{
        userService.deleteUser(email,password);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @PutMapping("")
    public ResponseEntity<User> createNewUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password) throws Exception{
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(email,password));
    }

}
