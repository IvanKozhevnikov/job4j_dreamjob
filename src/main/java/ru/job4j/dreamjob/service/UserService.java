package ru.job4j.dreamjob.service;

import org.sql2o.converters.ConverterException;
import ru.job4j.dreamjob.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> save(User user) throws ConverterException;

    Optional<User> findByEmailAndPassword(String email, String password);
}
