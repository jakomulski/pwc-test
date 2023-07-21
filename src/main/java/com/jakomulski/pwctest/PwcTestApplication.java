package com.jakomulski.pwctest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakomulski.pwctest.models.Country;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class PwcTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PwcTestApplication.class, args);
    }

    @Value("classpath:countries.json")
    private Resource countriesResource;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CountryRepository countryRepository() {
        try {
            List<Country> countries = new ObjectMapper().readValue(countriesResource.getFile(), new TypeReference<>() {
            });
            return () -> countries;
        } catch (IOException e) {
            return Collections::emptyList;
        }
    }
}
