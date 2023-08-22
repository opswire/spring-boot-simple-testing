package org.opswire.springbootmvc.service;

import org.opswire.springbootmvc.model.Person;
import org.opswire.springbootmvc.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person getById(int id) {
        return personRepository.findById(id).orElse(null);
    }

    public void add(Person person) {
        personRepository.save(person);
    }

    public void update(int id, Person updatedPerson) {
        Person personToBeUpdated = getById(id);

        personToBeUpdated.setName(updatedPerson.getName());

        personRepository.save(personToBeUpdated);
    }

    public void delete(int id) {
        personRepository.deleteById(id);
    }
}
