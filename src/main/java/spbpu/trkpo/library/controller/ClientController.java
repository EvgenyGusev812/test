package spbpu.trkpo.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spbpu.trkpo.library.entity.Client;
import spbpu.trkpo.library.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAll());
    }

    @PostMapping("/save")
    public ResponseEntity<Long> saveClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.saveClient(client));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Client> getClient(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(clientService.getById(id));
    }

    @DeleteMapping("/delete")
    public void deleteClient(@RequestBody Client client) {
        clientService.deleteClient(client);
    }

    @GetMapping("/get-by-fio")
    public ResponseEntity<List<Client>> getAllByFio(@RequestParam(name = "name") String name,
                                    @RequestParam(name = "surname") String surName,
                                    @RequestParam(name = "middlename") String middleName) {
        return ResponseEntity.ok(clientService.getAllByFio(name, surName, middleName));
    }

}
