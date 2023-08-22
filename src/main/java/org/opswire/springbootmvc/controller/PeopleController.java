package org.opswire.springbootmvc.controller;

import org.opswire.springbootmvc.model.Person;
import org.opswire.springbootmvc.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;

    @Autowired
    public PeopleController(PersonService personService) {
        this.personService = personService;
    }

    // all people
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personService.getAll());
        return "people/index";
    }

    // person by id
    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personService.getById(id));
        return "people/show";
    }

    // form for add person
    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    // add person
    @PostMapping()
    public String add(@ModelAttribute Person person, Model model) {
        personService.add(person);
        return "redirect:/people";
    }

    // from for edit person
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.getById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute Person person) {
        personService.update(id, person);
        return "redirect:/people/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }
}
