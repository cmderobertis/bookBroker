package net.cmderobertis.bookbroker.controllers;
import net.cmderobertis.bookbroker.models.Book;
import net.cmderobertis.bookbroker.models.User;
import net.cmderobertis.bookbroker.services.UserService;
import net.cmderobertis.bookbroker.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class BookController {
    @Autowired
    BookService service;
    @Autowired
    UserService userService;

    @GetMapping("/books/new")
    String createForm(@ModelAttribute("book") Book book, Model model, HttpSession session) {
        model.addAttribute("user", userService.getOne((Long) session.getAttribute("userId")));
        return "createBook.jsp";
    }
    @PostMapping("/books")
    String create(@ModelAttribute("book") Book book) {
        service.create(book);
        return "redirect:/books";
    }
    @GetMapping("/books")
    String showAll(Model model, HttpSession session) {
        User user = userService.getOne((Long) session.getAttribute("userId"));
        model.addAttribute("borrowedBooks", service.getBorrowedBooks(user));
        model.addAttribute("unborrowedBooks", service.getUnborrowedBooks(user));
        model.addAttribute("user", user);
        return "books.jsp";
    }


    @GetMapping("/books/{id}")
    String showOne(@PathVariable Long id, Model model, HttpSession session) {
        Book book = service.getOne(id);
        model.addAttribute("book", book);
        model.addAttribute("user", userService.getOne((Long) session.getAttribute("userId")));
        return "showBook.jsp";
    }
    @GetMapping("/books/{id}/edit")
    String updateForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        Book book = service.getOne(id);
        if (!Objects.equals((Long)session.getAttribute("userId"), book.getOwner().getId())) {
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "updateBook.jsp";
    }
    @PutMapping("/books/{id}")
    String update(
            @Valid
            @ModelAttribute("book") Book book,
            BindingResult result) {
        if (result.hasErrors()) {
            return "updateBook.jsp";
        } else {
            service.update(book);
            return "redirect:/books";
        }
    }
    @DeleteMapping("/books/{id}")
    String delete(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/books";
    }
    @PutMapping("/books/{id}/borrow")
    String borrowBook(@PathVariable("id") Long id, HttpSession session) {
        User borrower = userService.getOne((Long) session.getAttribute("userId"));
        Book book = service.getOne(id);
        service.borrow(book, borrower);
        return "redirect:/books";
    }
    @PutMapping("/books/{id}/return")
    String returnBook(@PathVariable("id") Long id) {
        Book book = service.getOne(id);
        service.returnBook(book);
        return "redirect:/books";
    }
}
