package spbpu.trkpo.library.service;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import spbpu.trkpo.library.entity.Book;
import spbpu.trkpo.library.entity.JournalRecord;
import spbpu.trkpo.library.exception.BookNotFoundException;
import spbpu.trkpo.library.exception.DataErrorException;
import spbpu.trkpo.library.exception.JournalNotFoundException;
import spbpu.trkpo.library.repository.BookRepository;
import spbpu.trkpo.library.repository.ClientRepository;
import spbpu.trkpo.library.repository.JournalRecordRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JournalRecordRepository journalRecordRepository;

    public List<Book> getAll() {
        List<Book> books = bookRepository.findAll();
        if (CollectionUtils.isEmpty(books)) {
            throw new BookNotFoundException();
        }
        return books;
    }

    public Book getById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
    }

    public Long saveBook(Book book) {
        beforeSave(book);
        return bookRepository.save(book).getId();
    }

    public void deleteBook(Book book) {
        if (book == null) {
            throw new BookNotFoundException();
        }
        bookRepository.delete(book);
    }

    public List<Book> getAllClientBooks(Long clientId) {
        List<JournalRecord> journalRecords = journalRecordRepository.findAllByClientId(clientId);
        Set<Book> books = new HashSet<>();
        journalRecords.forEach(
                e -> books.add(bookRepository.findById(e.getBookId()).get())
        );
        if (CollectionUtils.isEmpty(books)) {
            throw new BookNotFoundException();
        }
        return books.stream().toList();
    }

    private void beforeSave(Book book) {
        if (book == null) {
            throw new DataErrorException();
        }
        if (StringUtils.isEmpty(book.getName())) {
            throw new DataErrorException();
        }
    }

    public List<Book> getAllBooksByBookType(Long bookTypeId) {
        List<Book> books = bookRepository.findAllByTypeId(bookTypeId);
        if (CollectionUtils.isEmpty(books)) {
            throw new BookNotFoundException();
        }
        return books;
    }

    public Book getByName(String name) {
        Book book = bookRepository.findByName(name);
        if (book == null) {
            throw new BookNotFoundException();
        }
        return book;
    }

}
