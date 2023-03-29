package spbpu.trkpo.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import spbpu.trkpo.library.entity.Book;
import spbpu.trkpo.library.exception.BookNotFoundException;
import spbpu.trkpo.library.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;


    @GetMapping("/")
    public ResponseEntity<List<Book>> getALlBooks() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @PostMapping("/save-book")
    public ResponseEntity<Long> saveBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @DeleteMapping("/delete")
    public void deleteBook(@RequestBody Book book) {
        bookService.deleteBook(book);
    }


}
