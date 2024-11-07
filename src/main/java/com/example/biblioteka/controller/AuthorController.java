package com.example.biblioteka.controller;

import com.example.biblioteka.database.AuthorRepository;
import com.example.biblioteka.database.BookRepository;
import com.example.biblioteka.model.Author;
import com.example.biblioteka.model.Book;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/authors")
public class AuthorController {
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public String getAllAuthors(Model model) {
        List<Author> authors = authorRepository
                .findAll()
                .stream()
                .filter(author -> {return !author.getName().equals("Нет Автора");})
                .collect(Collectors.toList());
        model.addAttribute("authors", authors);
        return "authorsPage";
    }

    @GetMapping(path = "/{id}")
    public String getAuthorById(@PathVariable("id") Integer id, Model model) throws Exception {
        Author author = authorRepository.findById(id).orElseThrow(
                () -> new Exception("Автор с id = " + id + " не найден")
        );
        List<Book> booksByAuthor = bookRepository.findByAuthorId(id);
        model.addAttribute("author", author);
        model.addAttribute("books", booksByAuthor);
        return "authorPage";
    }

    @GetMapping(path = {"/new"})
    public String showAddingForm() {
        return "newAuthor";
    }

    @PostMapping(path = {"/new"})
    public String saveNewAuthor(@RequestParam("name") @NonNull String name)
    {
        Author author = new Author(name);
        authorRepository.saveAndFlush(author);
        return "redirect:/api/v1/authors";
    }
}
