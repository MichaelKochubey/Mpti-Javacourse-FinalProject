package com.example.biblioteka.controller;

import com.example.biblioteka.database.BookRepository;
import com.example.biblioteka.database.PersonRepository;
import com.example.biblioteka.database.ReadingRepository;
import com.example.biblioteka.model.Book;
import com.example.biblioteka.model.Person;
import com.example.biblioteka.model.Reading;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/readings")
public class ReadingController {
    @Autowired
    ReadingRepository readingRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public String getAllReadings(Model model) {
        List<Reading> readings = readingRepository.findAll();
        model.addAttribute("readings", readings);
        return "readingsPage";
    }

    @GetMapping(path = "/{id}")
    public String returnBook(@PathVariable("id") Integer id, Model model) throws Exception {
        Reading reading = readingRepository.findById(id).orElseThrow(
                () -> new Exception("Чтение с id = " + id + " не найдено")
        );
        readingRepository.delete(reading);
        return "redirect:/api/v1/readings";
    }

    @GetMapping(path = {"/new"})
    public String showAddingForm(Model model) {
        List<Reading> readings = readingRepository.findAll();
        List<Book> booksInRead = readings.stream().map(Reading::getBook).toList();
        List<Book> books = bookRepository.findAll().stream()
                .filter(book -> {return !booksInRead.contains(book);})
                .collect(Collectors.toList());
        List<Person> persons = personRepository.findAll();
        model.addAttribute("books", books);
        model.addAttribute("persons", persons);
        return "newReading";
    }

    @PostMapping(path = {"/new"})
    public String makeNewReading(@RequestParam("personsList") Integer personId,
                                @RequestParam("booksList") Integer bookId) throws Exception {
        Person person = personRepository.findById(personId).orElseThrow(
                () -> new Exception("Читатель с id = " + personId + " не найден")
        );
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new Exception("Книга с id = " + bookId + " не найдена")
        );
        Reading reading = new Reading(person, book);
        readingRepository.saveAndFlush(reading);
        return "redirect:/api/v1/readings";
    }
}
