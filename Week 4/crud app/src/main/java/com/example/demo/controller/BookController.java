package com.example.demo.controller;

import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    // Example mapping for the homepage or listing books
    @GetMapping("/")
    public String listBooks(Model model) {
        // model.addAttribute("books", bookService.listAllBooks());
        return "list_books"; // Renders src/main/resources/templates/list_books.html
    }

    // Mapping for showing the add book form
    @GetMapping("/add")
    public String addBookForm(Model model) {
        // model.addAttribute("book", new Book());
        return "add_book"; // Renders src/main/resources/templates/add_book.html
    }

    // Mapping for showing the edit book form
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable("id") long id, Model model) {
        // Book book = bookService.findBookById(id);
        // model.addAttribute("book", book);
        return "edit_book"; // Renders src/main/resources/templates/edit_book.html
    }

    // Example POST mapping for adding/updating a book
    @PostMapping("/save")
    public String saveBook(/*@ModelAttribute("book") Book book*/) {
        // bookService.saveBook(book);
        return "redirect:/"; // Redirects to the listing page after saving
    }
}