package net.cmderobertis.bookbroker.services;

import net.cmderobertis.bookbroker.models.Book;
import net.cmderobertis.bookbroker.models.User;
import net.cmderobertis.bookbroker.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository repo;
    public BookService(BookRepository repo) {
        this.repo = repo;
    }
    // CREATE
    public Book create(Book book) {
        return repo.save(book);
    }
    // READ
    public List<Book> getAll() {
        return repo.findAll();
    }
    public List<Book> getBorrowedBooks(User user) {
        return getAll().stream().filter((book) -> book.getBorrower() == user).collect(Collectors.toList());
    }
    public List<Book> getUnborrowedBooks(User user) {
        return getAll().stream().filter((book) -> book.getBorrower() != user).collect(Collectors.toList());
    }
    public Book getOne(Long id) {
        Optional<Book> book = repo.findById(id);
        return book.orElse(null);
    }
    // UPDATE
    public Book update(Book book) {
        return repo.save(book);
    }
    //DELETE
    public void delete(Long id) {
        repo.deleteById(id);
    }
    public void borrow(Book book, User borrower) {
        book.setBorrower(borrower);
        repo.save(book);
    }
    public void returnBook(Book book) {
        book.setBorrower(null);
        repo.save(book);
    }
}
