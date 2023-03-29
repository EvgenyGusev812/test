package spbpu.trkpo.library.controllerunit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import spbpu.trkpo.library.controller.ClientController;
import spbpu.trkpo.library.entity.Client;
import spbpu.trkpo.library.service.ClientService;
import spbpu.trkpo.library.utils.TestUtils;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @InjectMocks
    ClientController clientController;

    @Mock
    ClientService clientService;

    @Test
    void getAllClients() {
        Client client = TestUtils.createClientEntity();
        List<Client> clientList = List.of(client);
        Mockito.when(clientService.getAll()).thenReturn(clientList);
        ResponseEntity<List<Client>> responseEntity = clientController.getAllClients();
        Assertions.assertAll(
                () -> Assertions.assertEquals(clientList.size(), responseEntity.getBody().size()),
                () -> Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode())
        );
    }

    @Test
    void saveClients() {
        Client client = TestUtils.createClientEntity();
        Mockito.when(clientService.saveClient(Mockito.any())).thenReturn(client.getId());
        ResponseEntity<Long> responseEntity = clientController.saveClient(client);
        Assertions.assertAll(
                () -> Assertions.assertEquals(client.getId(), responseEntity.getBody()),
                () -> Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode())
        );
    }

    @Test
    void getClient() {
        Client client = TestUtils.createClientEntity();
        Mockito.when(clientService.getById(Mockito.any())).thenReturn(client);
        ResponseEntity<Client> responseEntity = clientController.getClient(client.getId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(client, responseEntity.getBody()),
                () -> Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode())
        );
    }

    @Test
    void deleteClient() {
        Mockito.doNothing().when(clientService).deleteClient(Mockito.any());
        Assertions.assertDoesNotThrow(() -> clientController.deleteClient(new Client()));
    }

    @Test
    void getByFio() {
        Client client = TestUtils.createClientEntity();
        List<Client> result = List.of(client);
        Mockito.when(clientService.getAllByFio(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(result);
        ResponseEntity<List<Client>> responseEntity = clientController.getAllByFio("1", "2", "3");
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.size(), responseEntity.getBody().size()),
                () -> Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode())
        );
    }
}
