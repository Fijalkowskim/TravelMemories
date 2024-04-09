package com.fijalkowskim.travelmemories.controllers;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.models.travels.Travel;
import com.fijalkowskim.travelmemories.services.PhotoService;
import com.fijalkowskim.travelmemories.services.StageService;
import com.fijalkowskim.travelmemories.services.TravelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public class TravelControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PhotoService photoService;

    @MockBean
    private StageService stageService;

    @MockBean
    private TravelService travelService;

    @Test
    public void GetTravels_ProperData_Success() throws Exception {
        Mockito.when(travelService.getTravels(Mockito.any(PageRequest.class), Mockito.anyString())).thenReturn(Page.empty());

        RequestBuilder request = MockMvcRequestBuilders.get("/api/travels")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void GetTravel_ProperData_Success() throws Exception {
        long travelId = 1L;
        Travel travel = new Travel();
        travel.setId(1L);

        Mockito.when(travelService.getTravelById(travelId)).thenReturn(travel);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/travels/" + travelId)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk());
    }


}