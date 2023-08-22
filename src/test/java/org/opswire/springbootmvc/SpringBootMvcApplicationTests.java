package org.opswire.springbootmvc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opswire.springbootmvc.controller.PeopleController;
import org.opswire.springbootmvc.repository.PersonRepository;
import org.opswire.springbootmvc.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootMvcApplicationTests {

    @Autowired
    PeopleController peopleController;

    @Autowired
    PersonService personService;

    @Autowired
    PersonRepository repository;

    @Test
    void mainTest() {
        SpringBootMvcApplication.main(new String[]{});
        Assertions.assertTrue(true);
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(peopleController);
        Assertions.assertNotNull(personService);
        Assertions.assertNotNull(repository);
    }

}
