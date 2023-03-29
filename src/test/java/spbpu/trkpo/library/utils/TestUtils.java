package spbpu.trkpo.library.utils;

import spbpu.trkpo.library.entity.Book;
import spbpu.trkpo.library.entity.BookType;
import spbpu.trkpo.library.entity.Client;
import spbpu.trkpo.library.entity.JournalRecord;

import java.sql.Timestamp;
import java.util.Set;

public final class TestUtils {

    public static Client createClientEntity() {
        return Client.builder()
                .id(1L)
                .firstName("test_first_name")
                .lastName("test_last_name")
                .patherName("test_pather_name")
                .passportNum("123356")
                .passportSeria("1233")
                .build();
    }

    public static JournalRecord createJournalRecordEntity() {
        Timestamp dateBeg = new Timestamp(1);
        Timestamp dateEnd = new Timestamp(100);
        Timestamp dateRet = new Timestamp(50);
        return JournalRecord.builder()
                .id(1L)
                .bookId(1L)
                .dateEnd(dateEnd)
                .dateRet(dateRet)
                .dateBeg(dateBeg)
                .clientId(1L)
                .build();
    }

    public static Book createBookEntity() {
        return Book.builder()
                .id(1L)
                .name("name")
                .cnt(1L)
                .typeId(1L)
                .journalRecords(Set.of(createJournalRecordEntity()))
                .build();
    }

    public static BookType createBookTypeEntity() {
        return BookType.builder()
                .id(1L)
                .cnt(1L)
                .name("test_name")
                .dayCount(10L)
                .fine(10L)
                .books(Set.of(createBookEntity()))
                .build();
    }
}
