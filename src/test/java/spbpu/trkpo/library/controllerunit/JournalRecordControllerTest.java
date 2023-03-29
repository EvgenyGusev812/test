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
import spbpu.trkpo.library.controller.JournalRecordController;
import spbpu.trkpo.library.entity.JournalRecord;
import spbpu.trkpo.library.service.JournalRecordService;
import spbpu.trkpo.library.utils.TestUtils;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class JournalRecordControllerTest {

    @InjectMocks
    JournalRecordController journalRecordController;

    @Mock
    JournalRecordService journalRecordService;

    @Test
    void getAllJournalRecords() {
        JournalRecord record = TestUtils.createJournalRecordEntity();
        List<JournalRecord> clientList = List.of(record);
        Mockito.when(journalRecordService.getAll()).thenReturn(clientList);
        ResponseEntity<List<JournalRecord>> responseEntity = journalRecordController.getALlJournalRecord();
        Assertions.assertAll(
                () -> Assertions.assertEquals(clientList.size(), responseEntity.getBody().size()),
                () -> Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode())
        );
    }

    @Test
    void saveJournalRecord() {
        JournalRecord record = TestUtils.createJournalRecordEntity();
        Mockito.when(journalRecordService.saveJournalRecord(Mockito.any())).thenReturn(record.getId());
        ResponseEntity<Long> responseEntity = journalRecordController.saveJournalRecord(record);
        Assertions.assertAll(
                () -> Assertions.assertEquals(record.getId(), responseEntity.getBody()),
                () -> Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode())
        );
    }

    @Test
    void getJournalRecord() {
        JournalRecord record = TestUtils.createJournalRecordEntity();
        Mockito.when(journalRecordService.getById(Mockito.any())).thenReturn(record);
        ResponseEntity<JournalRecord> responseEntity = journalRecordController.getJournalRecord(record.getId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(record, responseEntity.getBody()),
                () -> Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode())
        );
    }

    @Test
    void deleteJournalRecord() {
        Mockito.doNothing().when(journalRecordService).delete(Mockito.any());
        Assertions.assertDoesNotThrow(() -> journalRecordController.deleteBook(new JournalRecord()));
    }
}
