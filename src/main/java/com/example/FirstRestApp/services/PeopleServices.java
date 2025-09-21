package com.example.FirstRestApp.services;


import com.example.FirstRestApp.models.Person;
import com.example.FirstRestApp.repositories.PeopleRepository;
import com.example.FirstRestApp.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Transactional(readOnly = true)
@Service
public class PeopleServices {


    private final PeopleRepository peopleRepository;


    @Autowired
    public PeopleServices(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> person = peopleRepository.findById(id);

        return person.orElseThrow(PersonNotFoundException::new);
    }

    @Transactional
    public Person save(Person person) {
        enrichPerson(person);
        return peopleRepository.save(person);
    }


    private void enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedWho("ADMIN");
    }
}
