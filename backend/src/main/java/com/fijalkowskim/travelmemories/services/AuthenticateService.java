package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.models.users.User;
import com.fijalkowskim.travelmemories.models.users.UserRole;
import com.fijalkowskim.travelmemories.repositories.UserDAORepository;
import com.fijalkowskim.travelmemories.responsemodels.AuthenticateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthenticateService {
    private final UserDAORepository userDAORepository;
    private final JwtService jwtService;

    @Autowired
    public AuthenticateService(UserDAORepository userDAORepository, JwtService jwtService) {
        this.userDAORepository = userDAORepository;
        this.jwtService = jwtService;
    }

    public User authenticate(String email, String password)throws CustomHTTPException{
        Optional<User> user = userDAORepository.findByEmail(email);
        if (user.isEmpty()) throw new CustomHTTPException("There is no user with such email.", HttpStatus.NOT_FOUND);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        if( !encoder.matches(password, user.get().getPasswordHash())) throw new CustomHTTPException("Wrong password.", HttpStatus.BAD_REQUEST);
        return user.get();
    }

    public AuthenticateResponse login(String email, String password) throws CustomHTTPException {
       User user = authenticate(email, password);
        AuthenticateResponse response = new AuthenticateResponse();
        response.setUser(user);
        response.setToken(jwtService.generateToken(user));
        return response;
    }
    public AuthenticateResponse register(String email, String password) throws CustomHTTPException {
        if(userDAORepository.findByEmail(email).isPresent()) throw new CustomHTTPException("User with this email already exists.",HttpStatus.CONFLICT);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(encoder.encode(password));
        user.setRole(UserRole.USER);
        User newUser = userDAORepository.save(user);
        AuthenticateResponse response = new AuthenticateResponse();
        response.setUser(newUser);
        response.setToken(jwtService.generateToken(newUser));
        return response;
    }
}
