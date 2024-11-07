package com.example.biblioteka.controller;

import com.example.biblioteka.database.PersonRepository;
import com.example.biblioteka.database.ReadingRepository;
import com.example.biblioteka.model.Author;
import com.example.biblioteka.model.Book;
import com.example.biblioteka.model.Person;
import com.example.biblioteka.model.Reading;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/persons")
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    ReadingRepository readingRepository;

    @GetMapping
    public String getAllPersons(Model model) {
        List<Person> persons = personRepository.findAll();
        model.addAttribute("persons", persons);
        return "personsPage";
    }

    @GetMapping(path = "/{id}")
    public String getPersonById(@PathVariable("id") Integer id, Model model) throws Exception {
        Person person = personRepository.findById(id).orElseThrow(
                () -> new Exception("Читатель с id = " + id + " не найден")
        );
        List<Reading> booksOfPerson = readingRepository.findByPersonId(id);
        model.addAttribute("person", person);
        model.addAttribute("readings", booksOfPerson);
        return "personPage";
    }

    @GetMapping(path = {"/new"})
    public String showAddingForm() {
        return "newPerson";
    }

    @PostMapping(path = {"/new"})
    public String saveNewPerson(@RequestParam("name") @NonNull String name,
                                @RequestParam("address") String address)
    {
        Person person = new Person(name, address);
        personRepository.saveAndFlush(person);
        return "redirect:/api/v1/persons";
    }
}
