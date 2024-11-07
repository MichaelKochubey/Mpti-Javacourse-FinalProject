package com.example.biblioteka.controller;

import com.example.biblioteka.database.AuthorRepository;
import com.example.biblioteka.database.BookRepository;
import com.example.biblioteka.database.ReadingRepository;
import com.example.biblioteka.model.Author;
import com.example.biblioteka.model.Book;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/books")
public class BookController {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    ReadingRepository readingRepository;

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "booksPage";
    }

    @GetMapping(path = "/{id}")
    public String getBookById(@PathVariable("id") Integer id, Model model) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new Exception("Книга с id = " + id + " не найдена")
        );
        model.addAttribute("book", book);
        return "bookPage";
    }

    @PostMapping(path = "/{id}")
    public void saveEditingBook(@PathVariable("id") Integer id,
                                  @RequestBody Book book) throws Exception {
        Book oldBook = bookRepository.findById(id).orElseThrow(
                () -> new Exception("Книга с id = " + id + " не найдена")
        );
        book.setAuthor(oldBook.getAuthor());
        bookRepository.saveAndFlush(book);
    }

    @DeleteMapping(path = {"/{id}"})
    public void deleteSetting(@PathVariable("id") Integer id) throws Exception {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new Exception("Настройка с id = " + id + " не найдена"));
        if (readingRepository.findByBookId(book.getId()).isEmpty())
            bookRepository.delete(book);
        else System.out.println("Книга выдана читателю, невозможно удалить");
    }

    @GetMapping(path = {"/new"})
    public String showAddingForm(Model model) {
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "newBook";
    }

    @PostMapping(path = {"/new"})
    public String saveNewBook(@RequestParam("name") @NonNull String name,
                            @RequestParam("year") String year,
                            @RequestParam("authorList") String authorName,
                            @RequestParam("description") String description)
    {
        Author selectedAuthor = authorRepository.findByName(authorName);
        Book book = new Book(name, Integer.parseInt(year), selectedAuthor, description);
        bookRepository.saveAndFlush(book);
        return "redirect:/api/v1/books";
    }
}
