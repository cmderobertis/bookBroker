package net.cmderobertis.bookbroker.repositories;

import net.cmderobertis.bookbroker.models.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAll();
}
