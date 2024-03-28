package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.models.users.User;
import com.fijalkowskim.travelmemories.repositories.UserDAORepository;
import org.junit.Assert.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserDAORepository userDAORepository;
    @Mock
    private AuthenticateService authenticateService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Test
    public void testChangePassword_Successfully(){
        String newPassword = "newPassword";
        String oldPassword = "oldPassword";
        String email = "email";

        String hashedOldPassword = "$2a$12$hashedOldPassword";
        String hashedNewPassword = "$2a$12$hashedNewPassword";

        when(authenticateService.authenticate(email, oldPassword))
                .thenReturn(new User(email, hashedOldPassword));
        when(bCryptPasswordEncoder.encode(newPassword)).thenReturn(hashedNewPassword);

        userService.changePassword(email, oldPassword, newPassword);
        verify(userDAORepository).updatePasswordHashForUser(email, hashedNewPassword);
    }
}
