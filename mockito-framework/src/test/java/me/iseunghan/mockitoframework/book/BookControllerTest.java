package me.iseunghan.mockitoframework.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.iseunghan.mockitoframework.book.exception.BookDuplicateException;
import me.iseunghan.mockitoframework.book.exception.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 모든 빈들을 컨텍스트에 올리지 않고, MVC테스트를 위한 Spring MVC components (i.e. @Controller, @ControllerAdvice, @JsonComponent 등)만 올라간다.
@WebMvcTest(controllers = BookController.class) // 해당 컨트롤러만 context에 올려준다.
//@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    // WebMvcTest에는 @AutoConfigureMockMvc가 붙어있다.
    @Autowired
    private MockMvc mockMvc;

    // bookService를 mockBean으로 등록시킨다.
    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Book> bookList;

    @BeforeEach
    void setup() {
        this.bookList = new ArrayList<>();
        bookList.add(Book.builder()
                .id(1L)
                .title("title1")
                .build());
        bookList.add(Book.builder()
                .id(2L)
                .title("title2")
                .build());
        bookList.add(Book.builder()
                .id(3L)
                .title("title3")
                .build());
    }

    @Test
    void book_저장_200() throws Exception {
        // given
        Book book = Book.builder()
                .id(100L)
                .title("title100")
                .price(1000).build();

        // stubbing
        given(bookService.saveBook(any(Book.class))).willReturn(anyLong());

        // when & then
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 중복된_book_저장_400() throws Exception{
        // given
        Book book = bookList.get(0);

        given(bookService.saveBook(any(Book.class))).willThrow(BookDuplicateException.class);

        // when & then
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void 모든_book_조회_200() throws Exception {
        // given
        given(bookService.findBooks()).willReturn(bookList);

        // when & then
        mockMvc.perform(get("/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").exists())
                .andExpect(jsonPath("[1].id").exists())
                .andExpect(jsonPath("[2].id").exists());
    }

    @Test
    void 하나의_book_조회_200() throws Exception {
        // given
        Book book = bookList.get(0);

        given(bookService.findBook(anyLong())).willReturn(book);

        // when & then
        mockMvc.perform(get("/books/{id}", anyLong())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("title").exists());
    }

    @Test
    void 없는_book_조회_404() throws Exception {
        // given
        Book book = bookList.get(0);

        given(bookService.findBook(anyLong())).willThrow(BookNotFoundException.class);

        // when & then
        mockMvc.perform(get("/books/{id}", anyLong())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}