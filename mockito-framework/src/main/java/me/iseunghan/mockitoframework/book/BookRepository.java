package me.iseunghan.mockitoframework.book;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private final static Long sequence = 1L;
    @Getter
    private final static List<Book> bookList = new ArrayList<>();

    public Book findById(Long id) {
        for (Book b : bookList) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    public Long save(Book book) {
        bookList.add(book);
        return book.getId();
    }
}
