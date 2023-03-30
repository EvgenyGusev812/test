package spbpu.trkpo.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cnt")
    private Long cnt;

    @Column(name = "typeId")
    private Long typeId;

    @OneToMany(targetEntity = JournalRecord.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bookId", referencedColumnName = "id")
    private Set<JournalRecord> journalRecords;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(name, book.name) && Objects.equals(cnt, book.cnt) && Objects.equals(typeId, book.typeId) && Objects.equals(journalRecords, book.journalRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cnt, typeId, journalRecords);
    }
}
