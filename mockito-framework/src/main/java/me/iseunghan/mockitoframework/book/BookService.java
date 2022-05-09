package me.iseunghan.mockitoframework.book;

import me.iseunghan.mockitoframework.book.exception.BookDuplicateException;
import me.iseunghan.mockitoframework.book.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findBooks() {
        return bookRepository.findAll();
    }

    public Book findBook(Long id) {
        Book book = bookRepository.findById(id);

        if (book == null) {
            throw new BookNotFoundException();
        }

        return book;
    }

    public Long saveBook(Book book) {
        Book findOne = bookRepository.findById(book.getId());

        if (findOne != null) {
            throw new BookDuplicateException();
        }

        return bookRepository.save(book);
    }


}
