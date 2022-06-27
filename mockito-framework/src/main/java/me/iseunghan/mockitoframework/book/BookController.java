package me.iseunghan.mockitoframework.book;

import me.iseunghan.mockitoframework.book.exception.BookDuplicateException;
import me.iseunghan.mockitoframework.book.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books/{book_id}")
    public Book findBook(@PathVariable Long book_id) {
        return bookService.findBook(book_id);
    }

    @GetMapping("/books")
    public List<Book> findAll() {
        return bookService.findBooks();
    }

    @PostMapping("/books")
    public Long addBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @ExceptionHandler(BookDuplicateException.class)
    public ResponseEntity _400() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity _404() {
        return ResponseEntity.notFound().build();
    }
}
