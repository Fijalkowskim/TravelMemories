package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.models.users.User;
import com.fijalkowskim.travelmemories.repositories.UserDAORepository;
import com.fijalkowskim.travelmemories.responsemodels.AuthenticateResponse;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticateServiceTest {
    @InjectMocks
    @Spy
    private AuthenticateService authenticateService;
    @Mock
    private UserDAORepository userDAORepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testAuthenticate_Successfully(){
        String email = "user1@email.com";
        String password ="p";
        User user  = User.builder().email(email).passwordHash(password).build();
        when(userDAORepository.findByEmail(Mockito.anyString()))
                .thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        assertThat(authenticateService.authenticate(email, password)).isEqualTo(user);
    }
    @Test
    public void testAuthenticate_NoSuchUser(){
        String email = "nouser@email.com";
        when(userDAORepository.findByEmail(email))
                .thenReturn(Optional.empty());
        assertThatThrownBy( ()->{
            authenticateService.authenticate(email,"password");
        }).isInstanceOf(CustomHTTPException.class);
    }
    @Test
    public void testAuthenticate_WrongPassword(){
        String email = "user1@email.com";
        String password ="wrongPassword";
        User user  = User.builder().email(email).passwordHash("p").build();
        when(userDAORepository.findByEmail(Mockito.anyString()))
                .thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        assertThatThrownBy( ()->{
            authenticateService.authenticate(email,password);
        }).isInstanceOf(CustomHTTPException.class);
    }
    @Test
    public void testLogin_Successfully(){
        String email = "user1@email.com";
        String password = "password";
        String token = "token";
        User user = new User();
        when(authenticateService.authenticate(email,password)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(token);

        assertThat(authenticateService.login(email,password)).
                isEqualTo(AuthenticateResponse.builder().token(token).user(user).build());
    }
}
