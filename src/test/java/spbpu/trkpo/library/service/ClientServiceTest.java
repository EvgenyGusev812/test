package spbpu.trkpo.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;
import spbpu.trkpo.library.entity.Client;
import spbpu.trkpo.library.exception.ClientNotFoundException;
import spbpu.trkpo.library.exception.DataErrorException;
import spbpu.trkpo.library.repository.ClientRepository;
import spbpu.trkpo.library.utils.TestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static spbpu.trkpo.library.utils.TestUtils.createClientEntity;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest  {

    @Mock
    ClientRepository repository;

    @InjectMocks
    ClientService clientService;


    @Test
    void getAllClientSuccess() {
        Client client = createClientEntity();
        List<Client> clientList = List.of(client);
        Mockito.when(repository.findAll()).thenReturn(clientList);
        List<Client> clients = clientService.getAll();
        Assertions.assertAll(
                () -> Assertions.assertEquals(clientList.size(), clients.size()),
                () -> Assertions.assertEquals(clientList.get(0), clients.get(0))
        );
    }

    @Test
    void getAllClientException() {
        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertThrows(ClientNotFoundException.class, () -> clientService.getAll());
    }

    @Test
    void getAllClientByFioSuccess() {
        Client client = createClientEntity();
        List<Client> clientList = List.of(client);
        Mockito.when(repository.findAllByFirstNameAndLastNameAndPatherName(client.getFirstName(), client.getLastName(), client.getPatherName()))
                .thenReturn(clientList);
        List<Client> clients = clientService.getAllByFio(client.getFirstName(), client.getLastName(), client.getPatherName());
        Assertions.assertAll(
                () -> Assertions.assertEquals(clientList.size(), clients.size()),
                () -> Assertions.assertEquals(clientList.get(0), clients.get(0))
        );
    }

    @Test
    void getAllClientByFioException() {
        String randomString = UUID.randomUUID().toString();
        Mockito.when(repository.findAllByFirstNameAndLastNameAndPatherName(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Collections.emptyList());
        Assertions.assertThrows(ClientNotFoundException.class, () -> clientService.getAllByFio(randomString, randomString, randomString));
    }

    @Test
    void saveClientTestSuccess() {
        Client client = createClientEntity();
        Mockito.when(repository.save(client)).thenReturn(client);
        Long resultClientId = clientService.saveClient(client);
        Assertions.assertEquals(client.getId(), resultClientId);
    }

    @Test
    void saveClientEmptyFirstNameTest() {
        Client client = createClientEntity();
        client.setFirstName(null);
        Assertions.assertThrows(DataErrorException.class, () -> clientService.saveClient(client));
    }

    @Test
    void saveClientEmptyLastNameTest() {
        Client client = createClientEntity();
        client.setLastName(null);
        Assertions.assertThrows(DataErrorException.class, () -> clientService.saveClient(client));
    }

    @Test
    void saveClientIncorrectLengthOfPassportSeria() {
        Client client = createClientEntity();
        Mockito.when(repository.save(client)).thenReturn(client);
        client.setPassportSeria(UUID.randomUUID().toString());
        Assertions.assertThrows(DataErrorException.class, () -> clientService.saveClient(client));
        client.setPassportSeria("4715");
        Assertions.assertDoesNotThrow(() -> clientService.saveClient(client));
    }

    @Test
    void saveClientIncorrectLengthOfPassportNum() {
        Client client = createClientEntity();
        client.setPassportNum(UUID.randomUUID().toString());
        Assertions.assertThrows(DataErrorException.class, () -> clientService.saveClient(client));
    }

    @Test
    void deleteClientSuccess() {
        Client client = createClientEntity();
        Mockito.doNothing().when(repository).delete(client);
        Assertions.assertDoesNotThrow(() -> clientService.deleteClient(client));
    }

    @Test
    void deleteClientException() {
        Assertions.assertThrows(ClientNotFoundException.class, () -> clientService.deleteClient(null));
    }

    @Test
    void getById() {
        Client client = createClientEntity();
        Long clientId = client.getId();
        Mockito.when(repository.findById(clientId)).thenReturn(Optional.of(client));
        Client result = clientService.getById(clientId);
        Assertions.assertEquals(client, result);
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ClientNotFoundException.class, () -> clientService.getById(1L));
    }

    @Test
    void getByIdException() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ClientNotFoundException.class, () -> clientService.getById(1L));
    }

    @Test
    void getByPassportNumAndPassportSeriaEmptyPassportInfo() {
        Assertions.assertThrows(ClientNotFoundException.class, () -> clientService.getByPassportNumAndPassportSeria("", ""));
    }

    @Test
    void getByPassportNumAndPassportSeriaSuccess() {
        Client client = createClientEntity();
        Mockito.when(repository.findByPassportNumAndPassportSeria(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(client));
        Client result = clientService.getByPassportNumAndPassportSeria(client.getPassportNum(), client.getPassportSeria());
        Assertions.assertEquals(client, result);
    }

    @Test
    void getByPassportNumAndPassportSeriaNotFound() {
        Assertions.assertThrows(ClientNotFoundException.class, () -> clientService.getByPassportNumAndPassportSeria("34", "45"));
    }

}
