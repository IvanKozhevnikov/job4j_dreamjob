package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Vacancy;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryVacancyRepository implements VacancyRepository {

    private static final MemoryVacancyRepository INSTANCE = new MemoryVacancyRepository();

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private long millis = System.currentTimeMillis();

    private Timestamp timestamp = new Timestamp(millis);

    private LocalDateTime localDateTime = timestamp.toLocalDateTime();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer",
                "Skils: OOP, Collections, Syntax, Templates,"
                        + " Lambda, Stream API", localDateTime));
        save(new Vacancy(0, "Junior Java Developer",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API", localDateTime));
        save(new Vacancy(0, "Junior+ Java Developer",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API, SQL, JDBC", localDateTime));
        save(new Vacancy(0, "Middle Java Developer",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API, SQL, JDBC, HTTP, HTTPS, Servlet,"
                        + "JSP, MVC, JSLT, Filter, Session, Auth, "
                        + "HTML, JS, CSS, Mockito", localDateTime));
        save(new Vacancy(0, "Middle+ Java Developer",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API, SQL, JDBC, HTTP, HTTPS, Servlet,"
                        + "JSP, MVC, JSLT, Filter, Session, Auth, "
                        + "HTML, JS, CSS, Mockito, Spring IoC, "
                        + "Spring HibernateTemplate, Spring Data,"
                        + " Spring MVC, Spring Security, Spring Test, "
                        + "Spring Boot, Spring Web Service, Spring JMS", localDateTime));
        save(new Vacancy(0, "Senior Java Developer",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API, SQL, JDBC, HTTP, HTTPS, Servlet,"
                        + "JSP, MVC, JSLT, Filter, Session, Auth, "
                        + "HTML, JS, CSS, Mockito, Spring IoC, "
                        + "Spring HibernateTemplate, Spring Data,"
                        + " Spring MVC, Spring Security, Spring Test, "
                        + "Spring Boot, Spring Web Service, Spring JMS", localDateTime));
    }

    public static MemoryVacancyRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) ->
                new Vacancy(oldVacancy.getId(), vacancy.getTitle(), vacancy.getDescription(), localDateTime)) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }

}