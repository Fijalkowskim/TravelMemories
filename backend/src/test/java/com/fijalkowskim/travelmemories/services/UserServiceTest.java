package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.models.users.User;
import com.fijalkowskim.travelmemories.repositories.UserDAORepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertThrows;
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
    public void ChangePassword_ProperData_Success(){
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
    @Test
    public void ChangePassword_WrongPassword_ExceptionThrown(){
        String newPassword = "newPassword";
        String wrongPassword = "wrongPassword";
        String email = "email";

        when(authenticateService.authenticate(email, wrongPassword))
                .thenThrow(new CustomHTTPException("Wrong password.", HttpStatus.BAD_REQUEST));

        assertThatThrownBy(() -> {
            userService.changePassword(email, wrongPassword, newPassword);
        }).isInstanceOf(CustomHTTPException.class);
    }
    @Test
    public void ChangePassword_NoSuchUser_ExceptionThrown(){
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String wrongEmail = "wrongEmail";

        when(authenticateService.authenticate(wrongEmail, oldPassword))
                .thenThrow(new CustomHTTPException("No such user.", HttpStatus.BAD_REQUEST));

        assertThatThrownBy(() -> {
            userService.changePassword(wrongEmail, oldPassword, newPassword);
        }).isInstanceOf(CustomHTTPException.class);
    }
    @Test
    public void ChangePassword_NewPasswordSameAsOld_ExceptionThrown(){
        String newPassword = "oldPassword";
        String oldPassword = "oldPassword";
        String email = "email";

        String hashedOldPassword = "$2a$12$hashedOldPassword";

        when(authenticateService.authenticate(email, oldPassword))
                .thenReturn(new User(email, hashedOldPassword));

        assertThatThrownBy(() -> {
            userService.changePassword(email, oldPassword, newPassword);
        }).isInstanceOf(CustomHTTPException.class);
    }
}
