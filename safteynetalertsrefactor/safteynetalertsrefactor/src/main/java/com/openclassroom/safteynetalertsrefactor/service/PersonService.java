package com.openclassroom.safteynetalertsrefactor.service;

import com.openclassroom.safteynetalertsrefactor.model.Person;
import com.openclassroom.safteynetalertsrefactor.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private  final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person addPerson(Person person){
        personRepository.add(person);
        return person;
    }

    public synchronized boolean update(Person updatedPerson) {
        Optional<Person> pd = personRepository.findByName(updatedPerson.getFirstName(), updatedPerson.getLastName());
        if (pd.isEmpty()) {
            return false;
        }

        Person existing = pd.get();

        existing.setAddress(updatedPerson.getAddress());
        existing.setCity(updatedPerson.getCity());
        existing.setZip(updatedPerson.getZip());
        existing.setPhone(updatedPerson.getPhone());
        existing.setEmail(updatedPerson.getEmail());

        personRepository.persist();
        return true;
    }
}