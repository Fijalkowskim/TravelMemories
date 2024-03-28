package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.models.users.UserRole;
import com.fijalkowskim.travelmemories.repositories.UserDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fijalkowskim.travelmemories.models.users.User;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserDAORepository userDAORepository;
    private final AuthenticateService authenticateService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserDAORepository userDAORepository, AuthenticateService authenticateService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDAORepository = userDAORepository;
        this.authenticateService = authenticateService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void deleteUser(String email, String password) throws CustomHTTPException{
        User user = authenticateService.authenticate(email,password);
        userDAORepository.delete(user);
    }
    public void changePassword(String email,String oldPassword, String newPassword) throws CustomHTTPException{
        User user = authenticateService.authenticate(email,oldPassword);
        if(oldPassword.equals(newPassword)) throw new CustomHTTPException("Passwords must differ.",HttpStatus.BAD_REQUEST);
        userDAORepository.updatePasswordHashForUser(user.getEmail(),bCryptPasswordEncoder.encode(newPassword));
    }
}
