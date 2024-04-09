package com.fijalkowskim.travelmemories.controllers;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.responsemodels.AuthenticateResponse;
import com.fijalkowskim.travelmemories.services.AuthenticateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
public class AuthenticateControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthenticateService authenticateService;
    @Mock
    private AuthenticateResponse authenticateResponse;

    @Test
    public void Login_ProperData_Success() throws Exception {
        String email = "user1@email.com";
        String password = "password";

        Mockito.when(authenticateService.login(email, password)).thenReturn(authenticateResponse);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/public/authenticate/login")
                .param("email", email)
                .param("password", password)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void Register_ProperData_Success() throws Exception {
        String email = "user1@email.com";
        String password = "password";

        Mockito.when(authenticateService.register(email, password)).thenReturn(authenticateResponse);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/public/authenticate/register")
                .param("email", email)
                .param("password", password)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isCreated());
    }
}