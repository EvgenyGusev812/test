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
import spbpu.trkpo.library.controller.BookTypeController;
import spbpu.trkpo.library.controller.JournalRecordController;
import spbpu.trkpo.library.entity.BookType;
import spbpu.trkpo.library.entity.JournalRecord;
import spbpu.trkpo.library.service.BookTypeService;
import spbpu.trkpo.library.service.JournalRecordService;
import spbpu.trkpo.library.utils.TestUtils;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class BookTypeControllerTest {

    @InjectMocks
    BookTypeController bookTypeController;

    @Mock
    BookTypeService bookTypeService;

    @Test
    void getAllBookTypes() {
        BookType bookType = TestUtils.createBookTypeEntity();
        List<BookType> bookTypes = List.of(bookType);
        Mockito.when(bookTypeService.getAll()).thenReturn(bookTypes);
        ResponseEntity<List<BookType>> responseEntity = bookTypeController.getAllBookTypes();
        Assertions.assertAll(
                () -> Assertions.assertEquals(bookTypes.size(), responseEntity.getBody().size()),
                () -> Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode())
        );
    }

    @Test
    void saveBookType() {
        BookType bookType = TestUtils.createBookTypeEntity();
        Mockito.when(bookTypeService.saveBookType(Mockito.any())).thenReturn(bookType.getId());
        ResponseEntity<Long> responseEntity = bookTypeController.saveBookType(bookType);
        Assertions.assertAll(
                () -> Assertions.assertEquals(bookType.getId(), responseEntity.getBody()),
                () -> Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode())
        );
    }

    @Test
    void getBookType() {
        BookType bookType = TestUtils.createBookTypeEntity();
        Mockito.when(bookTypeService.getById(Mockito.any())).thenReturn(bookType);
        ResponseEntity<BookType> responseEntity = bookTypeController.getBookType(bookType.getId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(bookType, responseEntity.getBody()),
                () -> Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode())
        );
    }

    @Test
    void deleteBookType() {
        Mockito.doNothing().when(bookTypeService).deleteBookType(Mockito.any());
        Assertions.assertDoesNotThrow(() -> bookTypeController.deleteBookType(new BookType()));
    }
}
