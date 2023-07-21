package com.jakomulski.pwctest;

import com.jakomulski.pwctest.models.Country;

import java.util.List;

public interface CountryRepository {
    List<Country> getAll();
}
