package ru.job4j.dream.repository;

import org.sql2o.converters.ConverterException;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User user) throws ConverterException;

    Optional<User> findByEmailAndPassword(String email, String password);

    Collection<User> findAll();

    boolean deleteById(int id);
}