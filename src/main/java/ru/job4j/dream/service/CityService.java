package ru.job4j.dream.service;

import ru.job4j.dream.model.City;

import java.util.Collection;

public interface CityService {
    Collection<City> findAll();
}
