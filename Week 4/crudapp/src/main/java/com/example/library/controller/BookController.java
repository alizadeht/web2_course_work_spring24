package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // List all books
    @GetMapping("/")
    public String listBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "books";
    }

    // Show form to add a new book
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    // Save the new book
    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book) {
        bookRepository.save(book);
        return "redirect:/";
    }

    // Show form to update a book
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Book book = bookRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "update-book";
    }

    // Update a book
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") long id, @ModelAttribute("book") Book book) {
        bookRepository.save(book);
        return "redirect:/";
    }

    // Delete a book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        Book book = bookRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        bookRepository.delete(book);
        return "redirect:/";
    }
}
