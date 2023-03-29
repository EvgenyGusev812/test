package spbpu.trkpo.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import spbpu.trkpo.library.entity.Client;
import spbpu.trkpo.library.exception.ClientNotFoundException;
import spbpu.trkpo.library.exception.DataErrorException;
import spbpu.trkpo.library.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAll() {
        List<Client> clients = clientRepository.findAll();
        if (CollectionUtils.isEmpty(clients)) {
            throw new ClientNotFoundException();
        }
        return clients;
    }

    public List<Client> getAllByFio(String firstName, String lastName, String patherName) {
        List<Client> clients = clientRepository.findAllByFirstNameAndLastNameAndPatherName(firstName, lastName, patherName);
        if (CollectionUtils.isEmpty(clients)) {
            throw new ClientNotFoundException();
        }
        return clients;
    }

    public Long saveClient(Client client) {
        beforeSave(client);
        return clientRepository.save(client).getId();
    }

    public Client getById(Long id) {
        return clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
    }

    public void deleteClient(Client client) {
        if (client == null) {
            throw new ClientNotFoundException();
        }
        clientRepository.delete(client);
    }


    private void beforeSave(Client client) {
        if (client == null || client.getFirstName() == null || client.getLastName() == null) {
            throw new DataErrorException();
        }
        if (client.getPassportNum() == null || client.getPassportNum().length() != 6) {
            throw new DataErrorException();
        }
        if (client.getPassportSeria() == null || client.getPassportSeria().length() != 4) {
            throw new DataErrorException();
        }
    }


}
