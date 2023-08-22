package org.opswire.springbootmvc.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.opswire.springbootmvc.model.Person;
import org.opswire.springbootmvc.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PeopleController.class)
class PeopleControllerTest {

    private final String CONTENT_TYPE_WITH_CHARSET = "text/html;charset=utf-8";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    void testIndex() throws Exception {
        mockMvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE_WITH_CHARSET))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(view().name("people/index"))
                .andExpect(model().attributeExists("people"));
        Mockito.verify(personService).getAll();
    }

    @Test
    void testShow() throws Exception {
        int personId = 1;
        when(personService.getById(personId)).thenReturn(new Person());

        mockMvc.perform(get("/people/{id}", personId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE_WITH_CHARSET))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(view().name("people/show"))
                .andExpect(model().attributeExists("person"));
        Mockito.verify(personService).getById(personId);
    }

    @Test
    void testCreate() throws Exception {
        mockMvc.perform(get("/people/new"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE_WITH_CHARSET))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(view().name("people/new"))
                .andExpect(model().attributeExists("person"));
    }

    @Test
    void testAdd() throws Exception {
        mockMvc.perform(post("/people"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/people"));
        Mockito.verify(personService).add(any(Person.class));
    }

    @Test
    void testEdit() throws Exception {
        int personId = 1;
        when(personService.getById(personId)).thenReturn(new Person());

        mockMvc.perform(get("/people/{id}/edit", personId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE_WITH_CHARSET))
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(view().name("people/edit"))
                .andExpect(model().attributeExists("person"));
        Mockito.verify(personService).getById(personId);
    }

    @Test
    void testUpdate() throws Exception {
        int personId = 1;
        mockMvc.perform(patch("/people/{id}", personId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/people/" + personId));
        Mockito.verify(personService).update(eq(personId), any(Person.class));
    }

    @Test
    void testDelete() throws Exception {
        int personId = 1;
        mockMvc.perform(delete("/people/{id}", personId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/people"));
        Mockito.verify(personService).delete(personId);
    }
}
