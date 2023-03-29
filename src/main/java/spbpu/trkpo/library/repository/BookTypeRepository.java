package spbpu.trkpo.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spbpu.trkpo.library.entity.BookType;

@Repository
public interface BookTypeRepository extends JpaRepository<BookType, Long> {
}
