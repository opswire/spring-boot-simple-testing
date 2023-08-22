package org.opswire.springbootmvc.respository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opswire.springbootmvc.model.Person;
import org.opswire.springbootmvc.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void whenFindByName_thenReturnPerson() {
        Person person = new Person();
        person.setName("test4");
        testEntityManager.persist(person);
        testEntityManager.flush();

        Person found = personRepository.findByName("test4");

        Assertions.assertNotNull(found);
        Assertions.assertEquals(1, found.getId());
    }
}
