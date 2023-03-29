package spbpu.trkpo.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import spbpu.trkpo.library.entity.Client;
import spbpu.trkpo.library.service.ClientService;
import spbpu.trkpo.library.utils.TestUtils;


import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ClientControllerTest  {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    ClientService clientService;

    @Test
    void getClientById() throws Exception {
        Client client = TestUtils.createClientEntity();
        Long clientId = clientService.saveClient(client);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clients/get/" + clientId))
                .andExpect(jsonPath("$.id").value(clientId))
                .andExpect(status().isOk());
    }

    @Test
    void getAllClients() throws Exception {
        Client client = TestUtils.createClientEntity();
        clientService.saveClient(client);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clients"))
                .andExpect(status().isOk());

    }

    @Test
    void saveClient() throws Exception {
        Client client = TestUtils.createClientEntity();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/clients/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk());
    }


    @Test
    void getClientByFio() throws Exception {
        Client client = TestUtils.createClientEntity();
        clientService.saveClient(client);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("name", List.of(client.getFirstName()));
        map.put("surname", List.of(client.getLastName()));
        map.put("middlename", List.of(client.getPatherName()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clients/get-by-fio")
                        .params(map))
                .andExpect(status().isOk());
    }

    @Test
    void deleteClient() throws Exception {
        Client client = TestUtils.createClientEntity();
        clientService.saveClient(client);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/clients/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk());
    }

}
