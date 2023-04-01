package spbpu.trkpo.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spbpu.trkpo.library.entity.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByFirstNameAndLastNameAndPatherName(String firstName, String lastName, String patherName);

    Optional<Client> findByPassportNumAndPassportSeria(String passportNum, String passportSeria);
}
