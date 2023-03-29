package spbpu.trkpo.library.controllerunit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import spbpu.trkpo.library.controller.BookController;
import spbpu.trkpo.library.entity.Book;
import spbpu.trkpo.library.service.BookService;
import spbpu.trkpo.library.utils.TestUtils;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    BookController bookController;

    @Mock
    BookService bookService;

    @Test
    void getAllBooks() {
        Book book = TestUtils.createBookEntity();
        List<Book> books = List.of(book);
        Mockito.when(bookService.getAll()).thenReturn(books);
        ResponseEntity<List<Book>> responseEntity = bookController.getALlBooks();
        Assertions.assertAll(
                () -> Assertions.assertEquals(books.size(), responseEntity.getBody().size()),
                () -> Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode())
        );
    }

    @Test
    void saveBook() {
        Book book = TestUtils.createBookEntity();
        Mockito.when(bookService.saveBook(book)).thenReturn(book.getId());
        ResponseEntity<Long> responseEntity = bookController.saveBook(book);
        Assertions.assertAll(
                () -> Assertions.assertEquals(book.getId(), responseEntity.getBody()),
                () -> Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode())
        );
    }

    @Test
    void getBook() {
        Book book = TestUtils.createBookEntity();
        Mockito.when(bookService.getById(book.getId())).thenReturn(book);
        ResponseEntity<Book> responseEntity = bookController.getBook(book.getId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(book, responseEntity.getBody()),
                () -> Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode())
        );
    }

    @Test
    void deleteBook() {
        Mockito.doNothing().when(bookService).deleteBook(Mockito.any());
        Assertions.assertDoesNotThrow(() -> bookController.deleteBook(new Book()));
    }
}
