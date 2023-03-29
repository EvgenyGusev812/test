package spbpu.trkpo.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spbpu.trkpo.library.entity.JournalRecord;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface JournalRecordRepository extends JpaRepository<JournalRecord, Long> {

    List<JournalRecord> findAllByClientId(Long clientId);

    List<JournalRecord> findAllByDateEndAfter(Timestamp date);

    List<JournalRecord> findAllByBookId(Long bookId);

}
