package com.jakomulski.pwctest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Country(String cca3, List<String> borders) {
}
