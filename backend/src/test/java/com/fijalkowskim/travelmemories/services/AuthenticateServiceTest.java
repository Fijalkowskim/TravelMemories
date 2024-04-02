package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.models.users.User;
import com.fijalkowskim.travelmemories.repositories.UserDAORepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticateServiceTest {
    @InjectMocks
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

        User authenticatedUser = authenticateService.authenticate(email, password);

        assertEquals(authenticatedUser, user);
    }
    @Test
    public void testAuthenticate_NoSuchUser(){
        String email = "nouser@email.com";
        when(userDAORepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows( CustomHTTPException.class,()->{
            authenticateService.authenticate(email,"p");
        });
    }
}
