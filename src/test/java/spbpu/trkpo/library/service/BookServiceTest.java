package spbpu.trkpo.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import spbpu.trkpo.library.entity.Book;
import spbpu.trkpo.library.entity.JournalRecord;
import spbpu.trkpo.library.exception.BookNotFoundException;
import spbpu.trkpo.library.exception.DataErrorException;
import spbpu.trkpo.library.exception.JournalNotFoundException;
import spbpu.trkpo.library.repository.BookRepository;
import spbpu.trkpo.library.repository.ClientRepository;
import spbpu.trkpo.library.repository.JournalRecordRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static spbpu.trkpo.library.utils.TestUtils.createBookEntity;
import static spbpu.trkpo.library.utils.TestUtils.createJournalRecordEntity;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest  {

    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    JournalRecordRepository journalRecordRepository;

    @Mock
    ClientRepository clientRepository;

    @Test
    void getAllSuccess() {
        Book book = createBookEntity();
        List<Book> books = List.of(book);
        Mockito.when(bookRepository.findAll()).thenReturn(books);
        List<Book> result = bookService.getAll();
        Assertions.assertAll(
                () -> Assertions.assertEquals(books.size(), result.size()),
                () -> Assertions.assertEquals(books.get(0), result.get(0))
        );
    }

    @Test
    void getAllException() {
        Mockito.when(bookRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getAll());
    }

    @Test
    void saveBookSuccess() {
        Book book = createBookEntity();
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        Long resultBookId = bookService.saveBook(book);
        Assertions.assertEquals(book.getId(), resultBookId);
    }

    @Test
    void saveBookEmpty() {
        Assertions.assertThrows(DataErrorException.class, () -> bookService.saveBook(null));
    }

    @Test
    void saveBookEmptyName() {
        Book book = createBookEntity();
        book.setName("");
        Assertions.assertThrows(DataErrorException.class, () -> bookService.saveBook(book));
        book.setName(null);
        Assertions.assertThrows(DataErrorException.class, () -> bookService.saveBook(book));
    }

    @Test
    void saveBookEmptyType() {
        Book book = createBookEntity();
        book.setTypeId(null);
        Assertions.assertThrows(DataErrorException.class, () -> bookService.saveBook(book));
    }

    @Test
    void getByIdSuccess() {
        Book book = createBookEntity();
        Long bookId = book.getId();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Book resultBook = bookService.getById(bookId);
        Assertions.assertEquals(bookId, resultBook.getId());
    }

    @Test
    void getByIdException() {
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getById(1L));
    }

    @Test
    void deleteBookSuccess() {
        Book book = createBookEntity();
        Mockito.doNothing().when(bookRepository).delete(book);
        Assertions.assertDoesNotThrow(() -> bookService.deleteBook(book));
    }

    @Test
    void deleteBookException() {
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(null));
    }

    @Test
    void getAllBooksByBookType() {
        Book book = createBookEntity();
        List<Book> books = List.of(book);
        Mockito.when(bookRepository.findAllByTypeId(Mockito.anyLong())).thenReturn(books);
        List<Book> result = bookService.getAllBooksByBookType(1L);
        Assertions.assertEquals(result.get(0), books.get(0));
    }

    @Test
    void getAllBooksByBookTypeException() {
        Mockito.when(bookRepository.findAllByTypeId(Mockito.anyLong())).thenReturn(Collections.emptyList());
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getAllBooksByBookType(1L));
    }

    @Test
    void getBookByName() {
        Book book = createBookEntity();
        Mockito.when(bookRepository.findByName(Mockito.anyString())).thenReturn(book);
        Book result = bookService.getByName("123");
        Assertions.assertEquals(book, result);
    }

    @Test
    void getBookByNameException() {
        Mockito.when(bookRepository.findByName(Mockito.anyString())).thenReturn(null);
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getByName("123"));
    }

    @Test
    void getAllClientBooks() {
        JournalRecord journalRecord = createJournalRecordEntity();
        Book book = createBookEntity();
        List<JournalRecord> records = List.of(journalRecord);
        Mockito.when(journalRecordRepository.findAllByClientId(Mockito.anyLong())).thenReturn(records);
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));
        List<Book> result = bookService.getAllClientBooks(1L);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void getAllClientBooksJournalNotFound() {
        Mockito.when(journalRecordRepository.findAllByClientId(Mockito.anyLong())).thenReturn(Collections.emptyList());
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getAllClientBooks(1L));
    }



}
