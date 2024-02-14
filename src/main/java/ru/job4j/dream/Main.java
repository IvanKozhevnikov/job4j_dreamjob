package ru.job4j.dream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xmlunit.builder.Input;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

@SpringBootApplication
public class Main {

    private static String loadSysEnvIfNullThenConfig(String sysEnv, String key, Properties config) {
        String value = System.getenv(sysEnv);
        if (value == null) {
            value = config.getProperty(key);
        }
        return value;
    }

    private static Connection loadConnection() throws ClassNotFoundException, SQLException {
        var config = new Properties();
        try (InputStream in = Main.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            config.load(in);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        String url = loadSysEnvIfNullThenConfig("JDBC_URL", "datasource.url", config);
        String username = loadSysEnvIfNullThenConfig("JDBC_USERNAME", "datasource.username", config);
        String password = loadSysEnvIfNullThenConfig("JDBC_PASSWORD", "datasource.password", config);
        String driver = loadSysEnvIfNullThenConfig("JDBC_DRIVER", "datasource.driver-class-name", config);
        System.out.println("url=" + url);
        Class.forName(driver);
        return DriverManager.getConnection(
                url, username, password
        );
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
  /**      try (Connection connection = loadConnection()) {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from cities");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                System.out.println("id: " + id);
                System.out.println("Name: " + name);
                System.out.println("\n============================\n");
            }
        }*/
            SpringApplication.run(Main.class, args);

    }
}
