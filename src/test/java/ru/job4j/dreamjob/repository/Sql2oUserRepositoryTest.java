package ru.job4j.dreamjob.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2oException;
import ru.job4j.dream.configuration.DatasourceConfiguration;
import ru.job4j.dream.model.User;
import ru.job4j.dream.repository.Sql2oUserRepository;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Sql2oUserRepositoryTest {

   private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUsers() {
        var users = sql2oUserRepository.findAll();
        for (var user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    @Test
    public void whenSaveAndGetUniquePassword() {
        String email = "www@mail.ru";
        String pass = "798356";
        sql2oUserRepository.save(new User(0, email, "Anna", pass)).get();
        var findUser = sql2oUserRepository.findByEmailAndPassword(email, "1234567");
        assertThat(findUser.isEmpty()).isTrue();
    }

    @Test
    public void whenSaveAndGetSame() {
        String email = "lada@mail.ru";
        String pass = "00337";
        var user = sql2oUserRepository.save(new User(0, email, "Victor", pass)).get();
        var findUser = sql2oUserRepository.findByEmailAndPassword(email, pass).get();
        assertThat(findUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenSaveAnExistingPasswordAndUniqueEmail() {
        String email = "idea@ya.ru";
        String password = "223322";
        sql2oUserRepository.save(new User(0, email, "Denis", password)).get();
        var findUser = sql2oUserRepository.findByEmailAndPassword("ideawww@ya.ru", password);
        assertThat(findUser.isEmpty()).isTrue();
    }

    @Test
    public void whenSameMail1() {
        String email = "idea@ya.ru";
        String password = "123345";
        sql2oUserRepository.save(new User(1, email, "Ivan", password)).get();
        assertThrows(Sql2oException.class, () -> sql2oUserRepository
                .save(new User(2, email, "Elena", "9999999")));
    }
}