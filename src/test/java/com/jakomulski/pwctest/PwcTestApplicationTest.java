package com.jakomulski.pwctest;

import com.jakomulski.pwctest.models.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class PwcTestApplicationTest {

    @Autowired
    private CountryRepository countryRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldLoadCountriesFromFile() {
        // when
        List<Country> countries = countryRepository.getAll();
        // then
        assertFalse(countries.isEmpty());
    }
}
