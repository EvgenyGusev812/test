package spbpu.trkpo.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "journal")
public class JournalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bookId", nullable = false)
    private Long bookId;

    @Column(name = "clientId", nullable = false)
    private Long clientId;

    @Column(name = "dateBeg", nullable = false)
    private Timestamp dateBeg;

    @Column(name = "dateEnd", nullable = false)
    private Timestamp dateEnd;

    @Column(name = "dateRet")
    private Timestamp dateRet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JournalRecord that = (JournalRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(bookId, that.bookId) && Objects.equals(clientId, that.clientId) && Objects.equals(dateBeg, that.dateBeg) && Objects.equals(dateEnd, that.dateEnd) && Objects.equals(dateRet, that.dateRet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, clientId, dateBeg, dateEnd, dateRet);
    }
}
