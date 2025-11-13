package com.openclassroom.safteynetalertsrefactor.repository;


import com.openclassroom.safteynetalertsrefactor.model.Person;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {
    private static final String p = "persons";
    private final JSONFileReaderRepository JSONFileReaderRepository;

    private final List<Person> persons = new ArrayList<>();

    public PersonRepository(JSONFileReaderRepository JSONFileReaderRepository) {
        this.JSONFileReaderRepository = JSONFileReaderRepository;
    }

    @PostConstruct
    private void init() {
            List<Person> loaded = JSONFileReaderRepository.readList(p, Person.class);
            if (loaded != null) {
                persons.addAll(loaded);
            }
    }

    public List<Person> findAll() {
        return new ArrayList<>(persons);
    }

    public synchronized void add(Person newPerson) {
        persons.add(0, newPerson);
        JSONFileReaderRepository.writeList(p, persons);
    }

}