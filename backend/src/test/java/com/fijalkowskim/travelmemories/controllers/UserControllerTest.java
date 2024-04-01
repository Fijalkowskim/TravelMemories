package com.fijalkowskim.travelmemories.controllers;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;

    @Test
    public void testChangePassword_Successfully() throws Exception{
        String email = "user1@email.com";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        RequestBuilder request  = MockMvcRequestBuilders.put("/api/user/changePassword")
                .param("email", email)
                .param("oldPassword", oldPassword)
                .param("newPassword", newPassword)
                .contentType(MediaType.APPLICATION_JSON);
        this.mvc.perform(request)
                .andExpect(status().isOk());
    }
    @Test
    public void testChangePassword_SamePasswords() throws Exception{
        String email = "user1@email.com";
        String oldPassword = "oldPassword";
        String newPassword = "oldPassword";
        Mockito.doThrow(new CustomHTTPException("Passwords must differ", HttpStatus.BAD_REQUEST))
                .when(userService).changePassword(email,oldPassword,newPassword);
        RequestBuilder request  = MockMvcRequestBuilders.put("/api/user/changePassword")
                .param("email", email)
                .param("oldPassword", oldPassword)
                .param("newPassword", newPassword)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testChangePassword_WrongPassword() throws Exception{
        String email = "user1@email.com";
        String oldPassword = "wrongPassword";
        String newPassword = "newPassword";
        Mockito.doThrow(new CustomHTTPException("Wrong password", HttpStatus.BAD_REQUEST))
                .when(userService).changePassword(email,oldPassword,newPassword);
        RequestBuilder request  = MockMvcRequestBuilders.put("/api/user/changePassword")
                .param("email", email)
                .param("oldPassword", oldPassword)
                .param("newPassword", newPassword)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testChangePassword_NoSuchUser() throws Exception{
        String email = "baduser@email.com";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        Mockito.doThrow(new CustomHTTPException("No user with such email", HttpStatus.BAD_REQUEST))
                .when(userService).changePassword(email,oldPassword,newPassword);
        RequestBuilder request  = MockMvcRequestBuilders.put("/api/user/changePassword")
                .param("email", email)
                .param("oldPassword", oldPassword)
                .param("newPassword", newPassword)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testDeleteUser_Successfully() throws Exception{
        String email = "user1@email.com";
        String password = "p";
        RequestBuilder request  = MockMvcRequestBuilders.delete("/api/user")
                .param("email", email)
                .param("password", password)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk());
    }
    @Test
    public void testDeleteUser_WrongPassword() throws Exception{
        String email = "user1@email.com";
        String password = "wrongPassword";
        Mockito.doThrow(new CustomHTTPException("Wrong password", HttpStatus.BAD_REQUEST))
                .when(userService).deleteUser(email,password);

        RequestBuilder request  = MockMvcRequestBuilders.delete("/api/user")
                .param("email", email)
                .param("password", password)
                .contentType(MediaType.APPLICATION_JSON);
        this.mvc.perform(request)
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testDeleteUser_NoSuchUser() throws Exception{
        String email = "nouser@email.com";
        String password = "wrongPassword";
        Mockito.doThrow(new CustomHTTPException("No user with such email", HttpStatus.BAD_REQUEST))
                .when(userService).deleteUser(email,password);

        RequestBuilder request  = MockMvcRequestBuilders.delete("/api/user")
                .param("email", email)
                .param("password", password)
                .contentType(MediaType.APPLICATION_JSON);
        this.mvc.perform(request)
                .andExpect(status().isBadRequest());
    }
}
