package com.jakomulski.pwctest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakomulski.pwctest.models.Country;
import com.jakomulski.pwctest.models.Route;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoutingServiceTest {
    private static List<Country> countries;
    private RoutingService routingService;

    @BeforeAll
    static void beforeAll() throws IOException {
        ClassLoader classLoader = RoutingServiceTest.class.getClassLoader();
        countries = Collections.unmodifiableList(new ObjectMapper().readValue(new File(classLoader.getResource("countries.json").getFile()), new TypeReference<>() {
        }));
    }

    @BeforeEach
    void beforeEach() {
        CountryRepository countryRepository = mock(CountryRepository.class);
        when(countryRepository.getAll()).thenReturn(countries);
        routingService = new RoutingService(countryRepository);
    }

    @Test
    void shouldFindRoute_CzeToIta() {
        // When
        Optional<Route> route = routingService.findRoute("CZE", "ITA");
        // Then
        assertTrue(route.isPresent());
        assertEquals(new Route(List.of("CZE", "AUT", "ITA")), route.get());
    }

    @Test
    void shouldFindRoute_ItaToCze() {
        // When
        Optional<Route> route = routingService.findRoute("ITA", "CZE");
        // Then
        assertTrue(route.isPresent());
        assertEquals(new Route(List.of("ITA", "AUT", "CZE")), route.get());
    }

    @Test
    void shouldFindRoute_PrtToNor() {
        // When
        Optional<Route> route = routingService.findRoute("PRT", "NOR");
        // Then
        assertTrue(route.isPresent());
        assertEquals(new Route(List.of("PRT", "ESP", "FRA", "DEU", "POL", "RUS", "NOR")), route.get());
    }

    @Test
    void shouldNotFindRoute_AusToCze() {
        // When
        Optional<Route> route = routingService.findRoute("AUS", "CZE");
        // Then
        assertFalse(route.isPresent());
    }

    @Test
    void shouldNotFindRoute_CzeToAus() {
        // When
        Optional<Route> route = routingService.findRoute("CZE", "AUS");
        // Then
        assertFalse(route.isPresent());
    }

    @Test
    void shouldNotFindRoute_XyzToCze() {
        // When
        Optional<Route> route = routingService.findRoute("XYZ", "CZE");
        // Then
        assertFalse(route.isPresent());
    }

    @Test
    void shouldNotFindRoute_PolToAbc() {
        // When
        Optional<Route> route = routingService.findRoute("POL", "ABC");
        // Then
        assertFalse(route.isPresent());
    }

    @Test
    void shouldNotFindRoute_XyzToAbc() {
        // When
        Optional<Route> route = routingService.findRoute("XYZ", "ABC");
        // Then
        assertFalse(route.isPresent());
    }
}