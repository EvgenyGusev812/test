package spbpu.trkpo.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import spbpu.trkpo.library.entity.Book;
import spbpu.trkpo.library.entity.BookType;
import spbpu.trkpo.library.entity.JournalRecord;
import spbpu.trkpo.library.exception.DataErrorException;
import spbpu.trkpo.library.exception.JournalNotFoundException;
import spbpu.trkpo.library.repository.JournalRecordRepository;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static spbpu.trkpo.library.utils.TestUtils.*;

@ExtendWith(MockitoExtension.class)
class JournalRecordServiceTest  {

    @InjectMocks
    JournalRecordService journalRecordService;

    @Mock
    JournalRecordRepository journalRecordRepository;

    @Mock
    BookService bookService;

    @Test
    void getJournalRecordByClientAndBookTypeSuccess() {
        JournalRecord journalRecord = createJournalRecordEntity();
        Book book = createBookEntity();
        BookType bookType = createBookTypeEntity();

        List<JournalRecord> list = List.of(journalRecord);
        Mockito.when(journalRecordRepository.findAllByClientId(Mockito.anyLong())).thenReturn(list);
        Mockito.when(bookService.getById(Mockito.anyLong())).thenReturn(book);

        List<JournalRecord> result = journalRecordService.getJournalRecordByClientAndBookType(1L, bookType);
        Assertions.assertAll(
                () -> Assertions.assertEquals(list.get(0), result.get(0)),
                () -> Assertions.assertEquals(list.size(), result.size())
        );
    }

    @Test
    void getJournalRecordByClientAndBookTypeException() {
        BookType bookType = createBookTypeEntity();
        Mockito.when(journalRecordRepository.findAllByClientId(Mockito.anyLong())).thenReturn(Collections.emptyList());
        Assertions.assertThrows(JournalNotFoundException.class, () -> journalRecordService.getJournalRecordByClientAndBookType(1L, bookType));
    }

    @Test
    void getJournalRecordByClientAndBookTypeAndDateRegSuccess() {
        JournalRecord journalRecord = createJournalRecordEntity();
        Book book = createBookEntity();
        BookType bookType = createBookTypeEntity();

        List<JournalRecord> list = List.of(journalRecord);
        Mockito.when(journalRecordRepository.findAllByClientId(Mockito.anyLong())).thenReturn(list);
        Mockito.when(bookService.getById(Mockito.anyLong())).thenReturn(book);

        List<JournalRecord> result = journalRecordService.getJournalRecordByClientAndBookTypeAndDateReg(1L, bookType, new Timestamp(40));
        Assertions.assertAll(
                () -> Assertions.assertEquals(list.get(0), result.get(0)),
                () -> Assertions.assertEquals(list.size(), result.size())
        );
    }

    @Test
    void getJournalRecordByClientAndBookTypeAndDateRegException() {
        BookType bookType = createBookTypeEntity();
        Mockito.when(journalRecordRepository.findAllByClientId(Mockito.anyLong())).thenReturn(Collections.emptyList());
        Assertions.assertThrows(JournalNotFoundException.class, () -> journalRecordService.getJournalRecordByClientAndBookType(1L, bookType));
    }

    @Test
    void getOverdueOrdersSuccess() {
        JournalRecord journalRecord = createJournalRecordEntity();
        List<JournalRecord> journalRecords = List.of(journalRecord);
        Mockito.when(journalRecordRepository.findAllByDateEndAfter(Mockito.any())).thenReturn(journalRecords);
        List<JournalRecord> result = journalRecordService.getOverdueOrders();
        Assertions.assertAll(
                () -> Assertions.assertEquals(journalRecords.size(), result.size()),
                () -> Assertions.assertEquals(journalRecords.get(0), result.get(0))
        );
    }

    @Test
    void getOverdueOrdersException() {
        Mockito.when(journalRecordRepository.findAllByDateEndAfter(Mockito.any())).thenReturn(Collections.emptyList());
        Assertions.assertThrows(JournalNotFoundException.class, () -> journalRecordService.getOverdueOrders());
    }

    @Test
    void saveJournalRecordSuccess() {
        JournalRecord journalRecord = createJournalRecordEntity();
        Mockito.when(journalRecordRepository.save(journalRecord)).thenReturn(journalRecord);
        Long resultJournalRecordId = journalRecordService.saveJournalRecord(journalRecord);
        Assertions.assertEquals(journalRecord.getId(), resultJournalRecordId);
    }

    @Test
    void saveJournalRecordEmpty() {
        Assertions.assertThrows(DataErrorException.class, () -> journalRecordService.saveJournalRecord(null));
    }

    @Test
    void saveJournalRecordEmptyClientOrBook() {
        JournalRecord journalRecord = createJournalRecordEntity();
        journalRecord.setClientId(null);
        Assertions.assertThrows(DataErrorException.class, () -> journalRecordService.saveJournalRecord(journalRecord));
        journalRecord.setClientId(1L);
        journalRecord.setBookId(null);
        Assertions.assertThrows(DataErrorException.class, () -> journalRecordService.saveJournalRecord(journalRecord));
    }

    @Test
    void saveJournalRecordEmptyDates() {
        JournalRecord journalRecord = createJournalRecordEntity();
        journalRecord.setDateBeg(null);
        Assertions.assertThrows(DataErrorException.class, () -> journalRecordService.saveJournalRecord(journalRecord));
        journalRecord.setDateBeg(new Timestamp(1L));
        journalRecord.setDateEnd(null);
        Assertions.assertThrows(DataErrorException.class, () -> journalRecordService.saveJournalRecord(journalRecord));
    }

    @Test
    void getAllSuccess() {
        JournalRecord journalRecord = createJournalRecordEntity();
        List<JournalRecord> journalRecords = List.of(journalRecord);
        Mockito.when(journalRecordRepository.findAll()).thenReturn(journalRecords);
        List<JournalRecord> result = journalRecordService.getAll();
        Assertions.assertEquals(journalRecords.size(), result.size());
    }

    @Test
    void getAllEmpty() {
        Mockito.when(journalRecordRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertThrows(JournalNotFoundException.class, () -> journalRecordService.getAll());
    }

    @Test
    void getActualJournalRecordsByClientId() {
        JournalRecord journalRecord = createJournalRecordEntity();
        journalRecord.setDateEnd(new Timestamp(System.currentTimeMillis() + 100000000L));
        List<JournalRecord> journalRecords = List.of(journalRecord);
        Mockito.when(journalRecordRepository.findAllByClientId(Mockito.anyLong())).thenReturn(journalRecords);
        List<JournalRecord> result = journalRecordService.getActualJournalRecordsByClintId(journalRecord.getClientId());
        Assertions.assertEquals(result.get(0), journalRecords.get(0));
    }

    @Test
    void getActualJournalRecordsByClientIdException() {
        Mockito.when(journalRecordRepository.findAllByClientId(Mockito.anyLong())).thenReturn(Collections.emptyList());
        Assertions.assertThrows(JournalNotFoundException.class, () -> journalRecordService.getActualJournalRecordsByClintId(1L));
    }

    @Test
    void getJournalRecrodsByBook() {
        JournalRecord journalRecord = createJournalRecordEntity();
        List<JournalRecord> journalRecords = List.of(journalRecord);
        Mockito.when(journalRecordRepository.findAllByBookId(Mockito.anyLong())).thenReturn(journalRecords);
        List<JournalRecord> result = journalRecordService.getJournalRecordsByBook(journalRecord.getClientId());
        Assertions.assertEquals(result.get(0), journalRecords.get(0));
    }

    @Test
    void getJournalRecrodsByBookException() {
        Mockito.when(journalRecordRepository.findAllByBookId(Mockito.anyLong())).thenReturn(Collections.emptyList());
        Assertions.assertThrows(JournalNotFoundException.class, () -> journalRecordService.getJournalRecordsByBook(1L));
    }

    @Test
    void getById() {
        JournalRecord journalRecord = createJournalRecordEntity();
        Mockito.when(journalRecordRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(journalRecord));
        JournalRecord result = journalRecordService.getById(1L);
        Assertions.assertEquals(journalRecord, result);
    }

    @Test
    void getByIdException() {
        Mockito.when(journalRecordRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(JournalNotFoundException.class, () -> journalRecordService.getById(1L));
    }

    @Test
    void deleteJournalRecord() {
        JournalRecord journalRecord = createJournalRecordEntity();
        Mockito.doNothing().when(journalRecordRepository).delete(journalRecord);
        Assertions.assertDoesNotThrow(() -> journalRecordService.delete(journalRecord));
    }

    @Test
    void deleteJournalRecordException() {
        Assertions.assertThrows(JournalNotFoundException.class, () -> journalRecordService.delete(null));
    }

}
