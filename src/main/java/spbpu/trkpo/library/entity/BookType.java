package spbpu.trkpo.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bookTypes")
public class BookType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cnt")
    private Long cnt;

    @Column(name = "fine")
    private Long fine;

    @Column(name = "dayCount")
    private Long dayCount;

    @OneToMany(targetEntity = Book.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "typeId", referencedColumnName = "id")
    private Set<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookType bookType = (BookType) o;
        return Objects.equals(id, bookType.id) && Objects.equals(name, bookType.name) && Objects.equals(cnt, bookType.cnt) && Objects.equals(fine, bookType.fine) && Objects.equals(dayCount, bookType.dayCount) && Objects.equals(books, bookType.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cnt, fine, dayCount, books);
    }
}
