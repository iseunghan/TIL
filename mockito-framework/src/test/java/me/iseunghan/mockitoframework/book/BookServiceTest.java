package me.iseunghan.mockitoframework.book;

import me.iseunghan.mockitoframework.book.exception.BookDuplicateException;
import me.iseunghan.mockitoframework.book.exception.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    /**
     * @Mock 이 붙은 Mock객체들은
     * @InjectMocks가 붙은 Mock 객체에 주입시켜준다.
     * BookService 필드에 Repository가 있으므로 Mock객체를 주입시켜줘야하므로!
     * @InjectMocks는 @Mock이나 @Spy 객체를 자신의 멤버 클래스와 일치하면 주입시킴!
     */
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book spring_Book;

    @BeforeEach
    void initBook() {
        spring_Book = Book.builder()
                .id(100L)
                .title("Spring-boot")
                .price(25000)
                .build();
    }

    @Test
    void id로_Book을_찾을수_있다() {
        // given
        // 해당 행위를 하면 나올 결과를 미리 정해준다!
        when(bookRepository.findById(anyLong())).thenReturn(spring_Book);

        // when
        Book book = bookService.findBook(100L);

        // then
        verify(bookRepository, times(1)).findById(anyLong());   // 해당 메소드 호출이 1번 완료했는가?
        assertEquals(spring_Book.getId(), book.getId());    // 나오길 기대하는 값, 실행되서 나온 결과값
    }

    @Test
    void 잘못된_id로_Book을_찾으면_BookNotFoundException() {
        // given
        when(bookRepository.findById(anyLong())).thenReturn(null);

        // when & then
        assertThrows(BookNotFoundException.class, () -> bookService.findBook(200L));
        verify(bookRepository, times(1)).findById(anyLong());   // 해당 메소드 호출이 1번 완료했는가?
    }

    @Test
    void Book을_저장할_수있다() {
        // given
        when(bookRepository.save(any(Book.class))).thenReturn(spring_Book.getId());

        // when
        Long id = bookService.saveBook(spring_Book);

        // then
        verify(bookRepository).save(any(Book.class));
        assertEquals(spring_Book.getId(), id);
    }

    @Test
    void 중복된_Book을_저장하면_BookDuplicateException() {
        // given
        when(bookRepository.findById(anyLong())).thenReturn(spring_Book);   // already exist

        // when & then
        assertThrows(BookDuplicateException.class, () -> bookService.saveBook(spring_Book));
        verify(bookRepository).findById(anyLong());
        verify(bookRepository, times(0)).save(any(Book.class)); // exception으로 인해 호출 못함!
    }
}