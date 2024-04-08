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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthenticateService(UserDAORepository userDAORepository, JwtService jwtService, BCryptPasswordEncoder encoder) {
        this.userDAORepository = userDAORepository;
        this.jwtService = jwtService;
        this.bCryptPasswordEncoder = encoder;
    }


    public User authenticate(String email, String password)throws CustomHTTPException{
        Optional<User> user = userDAORepository.findByEmail(email);
        if (user.isEmpty()) throw new CustomHTTPException("There is no user with such email.", HttpStatus.NOT_FOUND);
        if( !bCryptPasswordEncoder.matches(password, user.get().getPasswordHash())) throw new CustomHTTPException("Wrong password.", HttpStatus.BAD_REQUEST);
        return user.get();
    }

    public AuthenticateResponse login(String email, String password) throws CustomHTTPException {
       User user = authenticate(email, password);
       return AuthenticateResponse.builder().user(user).token(jwtService.generateToken(user)).build();
    }
    public AuthenticateResponse register(String email, String password) throws CustomHTTPException {
        if(userDAORepository.findByEmail(email).isPresent()) throw new CustomHTTPException("User with this email already exists.",HttpStatus.CONFLICT);
        User user = User.builder()
                .email(email)
                .passwordHash(bCryptPasswordEncoder.encode(password))
                .role(UserRole.USER).build();
        User newUser = userDAORepository.save(user);
        return AuthenticateResponse.builder().user(newUser).token(jwtService.generateToken(user)).build();
    }
}
