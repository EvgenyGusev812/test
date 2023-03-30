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
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "patherName")
    private String patherName;

    @Column(name = "passportSeria", length = 4)
    private String passportSeria;

    @Column(name = "passportNum", length = 6)
    private String passportNum;

    @OneToMany(targetEntity = JournalRecord.class, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "clientId", referencedColumnName = "id")
    private Set<JournalRecord> journalRecords;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName) && Objects.equals(patherName, client.patherName) && Objects.equals(passportSeria, client.passportSeria) && Objects.equals(passportNum, client.passportNum) && Objects.equals(journalRecords, client.journalRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, patherName, passportSeria, passportNum, journalRecords);
    }
}
