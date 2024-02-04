package ru.job4j.dream.repository;

import ru.job4j.dream.model.City;

import java.util.Collection;

public interface CityRepository {
    Collection<City> findAll();
}
