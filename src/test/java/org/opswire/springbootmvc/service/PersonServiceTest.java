package org.opswire.springbootmvc.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opswire.springbootmvc.model.Person;
import org.opswire.springbootmvc.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonService personService;

    @Test
    void getAllTest() {
        Person person1 = new Person(1, "test1");
        Person person2 = new Person(2, "test2");
        List<Person> people = List.of(person1, person2);

        Mockito.when(personRepository.findAll()).thenReturn(people);

        Assertions.assertEquals(2, personService.getAll().size());
        verify(personRepository).findAll();
    }

    @Test
    void getByIdTest() {
        int personId = 1;
        Optional<Person> person = Optional.of(new Person(personId, "test1"));

        Mockito.when(personRepository.findById(personId)).thenReturn(person);

        Assertions.assertEquals("test1", personService.getById(1).getName());
        verify(personRepository).findById(personId);
    }

    @Test
    void addTest() {
        Person person = new Person(1, "test1");

        personService.add(person);

        verify(personRepository).save(person);
    }

    @Test
    void updateTest() {
        int personToBeUpdatedId = 1;
        Person updatedPerson = new Person(0, "test0");
        Person personToBeUpdated = new Person(personToBeUpdatedId, "test1");

        when(personRepository.findById(personToBeUpdatedId)).thenReturn(Optional.of(personToBeUpdated));
        personService.update(personToBeUpdatedId, updatedPerson);

        Assertions.assertEquals(personToBeUpdated.getName(), updatedPerson.getName());
        verify(personRepository).findById(personToBeUpdatedId);
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void deleteTest() {
        int personId = 1;

        personService.delete(personId);

        verify(personRepository).deleteById(personId);
    }
}
