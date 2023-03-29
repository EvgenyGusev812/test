package spbpu.trkpo.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spbpu.trkpo.library.entity.BookType;
import spbpu.trkpo.library.entity.JournalRecord;
import spbpu.trkpo.library.service.BookTypeService;
import spbpu.trkpo.library.service.JournalRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book-types")
public class BookTypeController {

    @Autowired
    private BookTypeService bookTypeService;

    @GetMapping("/")
    public ResponseEntity<List<BookType>> getAllBookTypes() {
        return ResponseEntity.ok(bookTypeService.getAll());
    }

    @PostMapping("/save-book-type")
    public ResponseEntity<Long> saveBookType(@RequestBody BookType bookType) {
        return ResponseEntity.ok(bookTypeService.saveBookType(bookType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookType> getBookType(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(bookTypeService.getById(id));
    }

    @DeleteMapping("/delete")
    public void deleteBookType(@RequestBody BookType bookType) {
        bookTypeService.deleteBookType(bookType);
    }
}
