package com.fijalkowskim.travelmemories.controllers;

import com.fijalkowskim.travelmemories.services.UserService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testChangePassword_Successfully() throws Exception{
        RequestBuilder request  = MockMvcRequestBuilders.put("/api/user/changePassword")
                .param("email", "user1@email.com")
                .param("oldPassword", "oldPassword")
                .param("newPassword", "newPassword")
                .contentType(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(status().isOk());
    }
}
