package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class MemoryCandidateRepository implements CandidateRepository {

    private final AtomicInteger nextId = new AtomicInteger(1);

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private long millis = System.currentTimeMillis();

    private Timestamp timestamp = new Timestamp(millis);

    private LocalDateTime localDateTime = timestamp.toLocalDateTime();

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Bill Gates",
                "Skils: OOP, Collections, Syntax, Templates,"
                        + " Lambda, Stream API", localDateTime));
        save(new Candidate(0, "Sergey Sukhorukov",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API", localDateTime));
        save(new Candidate(0, "Alexey Balabanov",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API, SQL, JDBC", localDateTime));
        save(new Candidate(0, "Alexey Balabanov",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API, SQL, JDBC, HTTP, HTTPS, Servlet,"
                        + "JSP, MVC, JSLT, Filter, Session, Auth, "
                        + "HTML, JS, CSS, Mockito", localDateTime));
        save(new Candidate(0, "Vladis Pelsh",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API, SQL, JDBC, HTTP, HTTPS, Servlet,"
                        + "JSP, MVC, JSLT, Filter, Session, Auth, "
                        + "HTML, JS, CSS, Mockito, Spring IoC, "
                        + "Spring HibernateTemplate, Spring Data,"
                        + " Spring MVC, Spring Security, Spring Test, "
                        + "Spring Boot, Spring Web Service, Spring JMS", localDateTime));
        save(new Candidate(0, "Alla Pugacheva",
                "Skils: Pro OOP, Collections, InputStreams,"
                        + " OutputStreams, Scanner, Lambda,"
                        + " Stream API, SQL, JDBC, HTTP, HTTPS, Servlet,"
                        + "JSP, MVC, JSLT, Filter, Session, Auth, "
                        + "HTML, JS, CSS, Mockito, Spring IoC, "
                        + "Spring HibernateTemplate, Spring Data,"
                        + " Spring MVC, Spring Security, Spring Test, "
                        + "Spring Boot, Spring Web Service, Spring JMS", localDateTime));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId.getAndIncrement());
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate) ->
                new Candidate(oldCandidate.getId(), candidate.getName(), candidate.getDescription(), localDateTime)) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
