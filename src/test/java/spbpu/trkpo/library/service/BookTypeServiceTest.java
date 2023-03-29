package spbpu.trkpo.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import spbpu.trkpo.library.entity.BookType;
import spbpu.trkpo.library.exception.BookTypeNotFoundException;
import spbpu.trkpo.library.exception.DataErrorException;
import spbpu.trkpo.library.repository.BookTypeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static spbpu.trkpo.library.utils.TestUtils.createBookTypeEntity;

@ExtendWith(MockitoExtension.class)
class BookTypeServiceTest  {

    @InjectMocks
    BookTypeService bookTypeService;

    @Mock
    BookTypeRepository bookTypeRepository;

    @Test
    void saveBookSuccess() {
        BookType bookType = createBookTypeEntity();
        Mockito.when(bookTypeRepository.save(bookType)).thenReturn(bookType);
        Long resultBookTypeId = bookTypeService.saveBookType(bookType);
        Assertions.assertEquals(bookType.getId(), resultBookTypeId);
    }

    @Test
    void saveBookEmpty() {
        Assertions.assertThrows(DataErrorException.class, () -> bookTypeService.saveBookType(null));
    }

    @Test
    void saveBookEmptyName() {
        BookType bookType = createBookTypeEntity();
        bookType.setName("");
        Assertions.assertThrows(DataErrorException.class, () -> bookTypeService.saveBookType(bookType));
        bookType.setName(null);
        Assertions.assertThrows(DataErrorException.class, () -> bookTypeService.saveBookType(bookType));
    }

    @Test
    void getAll() {
        BookType bookType = createBookTypeEntity();
        List<BookType> bookTypeList = List.of(bookType);
        Mockito.when(bookTypeRepository.findAll()).thenReturn(bookTypeList);
        List<BookType> bookTypes = bookTypeService.getAll();
        Assertions.assertEquals(bookTypeList.size(), bookTypes.size());
    }

    @Test
    void getAllException() {
        Mockito.when(bookTypeRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertThrows(BookTypeNotFoundException.class, () -> bookTypeService.getAll());
    }

    @Test
    void getById() {
        BookType bookType = createBookTypeEntity();
        Mockito.when(bookTypeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(bookType));
        BookType result = bookTypeService.getById(bookType.getId());
        Assertions.assertEquals(bookType, result);
    }

    @Test
    void getByIdException() {
        Mockito.when(bookTypeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(BookTypeNotFoundException.class, () -> bookTypeService.getById(1L));
    }

    @Test
    void deleteBookType() {
        BookType bookType = createBookTypeEntity();
        Mockito.doNothing().when(bookTypeRepository).delete(bookType);
        Assertions.assertDoesNotThrow(() -> bookTypeService.deleteBookType(bookType));
    }

    @Test
    void deleteBookTypeException() {
        Assertions.assertThrows(BookTypeNotFoundException.class, () -> bookTypeService.deleteBookType(null));
    }


}
