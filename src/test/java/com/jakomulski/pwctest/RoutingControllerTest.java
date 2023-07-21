package com.jakomulski.pwctest;

import com.jakomulski.pwctest.models.Route;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoutingController.class)
class RoutingControllerTest {

    @MockBean
    private RoutingService routingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetRoute() throws Exception {
        when(routingService.findRoute("CZE", "ITA")).thenReturn(Optional.of(new Route(List.of("CZE", "AUT", "ITA"))));

        mockMvc.perform(MockMvcRequestBuilders.get("/routing/CZE/ITA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.route", Matchers.is(List.of("CZE", "AUT", "ITA"))));
    }

    @Test
    void shouldNotGetRoute() throws Exception {
        when(routingService.findRoute("CZE", "AUS")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/routing/CZE/ITA"))
                .andExpect(status().isBadRequest());
    }
}