package spbpu.trkpo.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spbpu.trkpo.library.entity.BookType;
import spbpu.trkpo.library.entity.JournalRecord;
import spbpu.trkpo.library.service.JournalRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/journal-records")
public class JournalRecordController {

    @Autowired
    private JournalRecordService journalRecordService;

    @GetMapping("/")
    public ResponseEntity<List<JournalRecord>> getALlJournalRecord() {
        return ResponseEntity.ok(journalRecordService.getAll());
    }

    @PostMapping("/save")
    public ResponseEntity<Long> saveJournalRecord(@RequestBody JournalRecord journalRecord) {
        return ResponseEntity.ok(journalRecordService.saveJournalRecord(journalRecord));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalRecord> getJournalRecord(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(journalRecordService.getById(id));
    }

    @DeleteMapping("/delete")
    public void deleteBook(@RequestBody JournalRecord journalRecord) {
        journalRecordService.delete(journalRecord);
    }

    @GetMapping("/by-client-and-type/{client}")
    public ResponseEntity<List<JournalRecord>> getJournalRecordByClientAndBookType(@PathVariable(name = "client") Long clientId,
                                                                                   @RequestBody BookType bookType) {
        return ResponseEntity.ok(journalRecordService.getJournalRecordByClientAndBookType(clientId, bookType));
    }

}
