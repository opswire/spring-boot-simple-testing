package org.opswire.springbootmvc.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.opswire.springbootmvc.model.Person;
import org.opswire.springbootmvc.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PeopleControllerIntegrationTest {
    private final String CONTENT_TYPE_WITH_CHARSET = "text/html;charset=utf-8";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService personService;

    private void initPeople() {
        Person person1 = new Person();
        person1.setName("test1");
        Person person2 = new Person();
        person2.setName("test2");
        personService.add(person1);
        personService.add(person2);
    }

    @Test
    @Order(1)
    void testIndex() throws Exception {
        initPeople();
        mockMvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE_WITH_CHARSET))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(view().name("people/index"))
                .andExpect(model().attributeExists("people"));

        Assertions.assertEquals(2, personService.getAll().size());
    }

    @Test
    @Order(2)
    void testCreate() throws Exception {
        mockMvc.perform(get("/people/new"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE_WITH_CHARSET))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(view().name("people/new"))
                .andExpect(model().attributeExists("person"));
    }

    @Test
    @Order(3)
    void testAdd() throws Exception {
        String personName = "test3";

        mockMvc.perform(post("/people").param("name", personName))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/people"));

        Assertions.assertEquals(3, personService.getAll().size());
    }

    @Test
    @Order(4)
    void testShow() throws Exception {
        int personId = 3;
        Person foundPerson = personService.getById(personId);

        mockMvc.perform(get("/people/{id}", personId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE_WITH_CHARSET))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(view().name("people/show"))
                .andExpect(model().attribute("person", foundPerson));
    }

    @Test
    @Order(5)
    void testEdit() throws Exception {
        int personId = 3;
        Person foundPerson = personService.getById(personId);

        mockMvc.perform(get("/people/{id}/edit", personId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE_WITH_CHARSET))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(view().name("people/edit"))
                .andExpect(model().attribute("person", foundPerson));
    }

    @Test
    @Order(6)
    void testUpdate() throws Exception {
        int personId = 3;
        String updatedName = "test33";

        mockMvc.perform(patch("/people/{id}", personId).param("name", updatedName))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/people/" + personId));

        Assertions.assertEquals(updatedName, personService.getById(personId).getName());
    }

    @Test
    @Order(7)
    void testDelete() throws Exception {
        int personId = 3;

        mockMvc.perform(delete("/people/{id}", personId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/people"));

        Assertions.assertNull(personService.getById(personId));
    }
}
