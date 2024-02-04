package ru.job4j.dream.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dream.model.Vacancy;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class MemoryVacancyRepository implements VacancyRepository {

    private AtomicInteger nextId = new AtomicInteger(1);

    private final Map<Integer, Vacancy> vacancies = new ConcurrentHashMap<>();

    private long millis = System.currentTimeMillis();

    private Timestamp timestamp = new Timestamp(millis);

    private LocalDateTime localDateTime = timestamp.toLocalDateTime();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer",
                "Skils: OOP, Collections, Syntax, Templates,"
                        + " Lambda, Stream API", localDateTime, true, 1, 0));
        save(new Vacancy(0, "Junior Java Developer",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API", localDateTime, true, 2, 0));
        save(new Vacancy(0, "Junior+ Java Developer",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API, SQL, JDBC", localDateTime, true, 3, 0));
        save(new Vacancy(0, "Middle Java Developer",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API, SQL, JDBC, HTTP, HTTPS, Servlet,"
                        + "JSP, MVC, JSLT, Filter, Session, Auth, "
                        + "HTML, JS, CSS, Mockito", localDateTime, true, 2, 0));
        save(new Vacancy(0, "Middle+ Java Developer",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API, SQL, JDBC, HTTP, HTTPS, Servlet,"
                        + "JSP, MVC, JSLT, Filter, Session, Auth, "
                        + "HTML, JS, CSS, Mockito, Spring IoC, "
                        + "Spring HibernateTemplate, Spring Data,"
                        + " Spring MVC, Spring Security, Spring Test, "
                        + "Spring Boot, Spring Web Service, Spring JMS",
                localDateTime, true, 2, 0));
        save(new Vacancy(0, "Senior Java Developer",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API, SQL, JDBC, HTTP, HTTPS, Servlet,"
                        + "JSP, MVC, JSLT, Filter, Session, Auth, "
                        + "HTML, JS, CSS, Mockito, Spring IoC, "
                        + "Spring HibernateTemplate, Spring Data,"
                        + " Spring MVC, Spring Security, Spring Test, "
                        + "Spring Boot, Spring Web Service, Spring JMS",
                localDateTime, true, 2, 0));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId.getAndIncrement());
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> {
            return new Vacancy(
                    oldVacancy.getId(), vacancy.getTitle(), vacancy.getDescription(),
                    vacancy.getCreationDate(), vacancy.getVisible(), vacancy.getCityId(), vacancy.getFileId());
        }) != null;
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