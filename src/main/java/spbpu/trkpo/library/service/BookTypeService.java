package spbpu.trkpo.library.service;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import spbpu.trkpo.library.entity.BookType;
import spbpu.trkpo.library.exception.BookNotFoundException;
import spbpu.trkpo.library.exception.BookTypeNotFoundException;
import spbpu.trkpo.library.exception.DataErrorException;
import spbpu.trkpo.library.repository.BookTypeRepository;

import java.util.List;

@Service
public class BookTypeService {

    @Autowired
    private BookTypeRepository bookTypeRepository;

    public List<BookType> getAll() {
        List<BookType> bookTypes = bookTypeRepository.findAll();
        if (CollectionUtils.isEmpty(bookTypes)) {
            throw new BookTypeNotFoundException();
        }
        return bookTypes;
    }

    public BookType getById(Long id) {
        return bookTypeRepository.findById(id).orElseThrow(BookTypeNotFoundException::new);
    }

    public Long saveBookType(BookType bookType) {
        beforeSave(bookType);
        return bookTypeRepository.save(bookType).getId();
    }

    public void deleteBookType(BookType bookType) {
        if (bookType == null) {
            throw new BookTypeNotFoundException();
        }
        bookTypeRepository.delete(bookType);
    }

    private void beforeSave(BookType bookType) {
        if (bookType == null) {
            throw new DataErrorException();
        }
        if (StringUtils.isEmpty(bookType.getName())) {
            throw new DataErrorException();
        }
    }
}
