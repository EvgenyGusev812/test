package spbpu.trkpo.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import spbpu.trkpo.library.entity.Book;
import spbpu.trkpo.library.entity.BookType;
import spbpu.trkpo.library.entity.JournalRecord;
import spbpu.trkpo.library.exception.DataErrorException;
import spbpu.trkpo.library.exception.JournalNotFoundException;
import spbpu.trkpo.library.repository.JournalRecordRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JournalRecordService {

    @Autowired
    private JournalRecordRepository journalRecordRepository;

    @Autowired
    private BookService bookService;

    public List<JournalRecord> getJournalRecordByClientAndBookType(Long clientId, BookType bookType) {
        List<JournalRecord> journalRecords = journalRecordRepository.findAllByClientId(clientId);
        journalRecords = journalRecords.stream()
                .filter(e -> {
                    Book book = bookService.getById(e.getBookId());
                    return bookType.getBooks().contains(book);
                })
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(journalRecords)) {
            throw new JournalNotFoundException();
        }
        return journalRecords;
    }

    public List<JournalRecord> getJournalRecordByClientAndBookTypeAndDateReg(Long clientId, BookType bookType, Timestamp d1) {
        List<JournalRecord> journalRecords = this.getJournalRecordByClientAndBookType(clientId, bookType);
        journalRecords = journalRecords.stream()
                .filter(e -> d1.after(e.getDateBeg()) && d1.before(e.getDateEnd()))
                .collect(Collectors.toList());
        return journalRecords;
    }

    public List<JournalRecord> getOverdueOrders() {
        return this.getOrdersByDateEndAfter(new Timestamp(System.currentTimeMillis()));
    }


    public Long saveJournalRecord(JournalRecord journalRecord) {
        beforeSave(journalRecord);
        return journalRecordRepository.save(journalRecord).getId();
    }


    public List<JournalRecord> getAll() {
        List<JournalRecord> journalRecords = journalRecordRepository.findAll();
        if (CollectionUtils.isEmpty(journalRecords)) {
            throw new JournalNotFoundException();
        }
        return journalRecords;
    }

    public List<JournalRecord> getActualJournalRecordsByClintId(Long clientId) {
        List<JournalRecord> journalRecords = journalRecordRepository.findAllByClientId(clientId);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        journalRecords = journalRecords.stream()
                .filter(e -> e.getDateEnd().after(now))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(journalRecords)) {
            throw new JournalNotFoundException();
        }
        return  journalRecords;
    }

    public List<JournalRecord> getJournalRecordsByBook(Long bookId) {
        List<JournalRecord> journalRecords = journalRecordRepository.findAllByBookId(bookId);
        if (CollectionUtils.isEmpty(journalRecords)) {
            throw new JournalNotFoundException();
        }
        return journalRecords;
    }

    public JournalRecord getById(Long id) {
        return journalRecordRepository.findById(id).orElseThrow(JournalNotFoundException::new);
    }

    public void delete(JournalRecord journalRecord) {
        if (journalRecord == null) {
            throw new JournalNotFoundException();
        }
        journalRecordRepository.delete(journalRecord);
    }

    private void beforeSave(JournalRecord journalRecord) {
        if (journalRecord == null) {
            throw new DataErrorException();
        }
        if (journalRecord.getBookId() == null || journalRecord.getClientId() == null) {
            throw new DataErrorException();
        }
        if (journalRecord.getDateBeg() == null || journalRecord.getDateEnd() == null) {
            throw new DataErrorException();
        }
    }

    private List<JournalRecord> getOrdersByDateEndAfter(Timestamp date) {
        List<JournalRecord> journalRecords = journalRecordRepository.findAllByDateEndAfter(date);
        if (CollectionUtils.isEmpty(journalRecords)) {
            throw new JournalNotFoundException();
        }
        return journalRecords;
    }

}
