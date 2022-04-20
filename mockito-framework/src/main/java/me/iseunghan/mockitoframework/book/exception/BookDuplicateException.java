package me.iseunghan.mockitoframework.book.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookDuplicateException extends RuntimeException {

    public BookDuplicateException(String message) {
        super(message);
    }
}
