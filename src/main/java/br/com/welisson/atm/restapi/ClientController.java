package br.com.welisson.atm.restapi;

import br.com.welisson.atm.domain.Client;
import br.com.welisson.atm.domain.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atm/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Client createClient(@RequestBody final Client client){
        return clientService.createClient(client);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Client updateClient(@RequestBody final Client client){
        return clientService.updateClient(client);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable("id") final Long id){
        clientService.deleteClient(id);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Client> listClients(){
        return clientService.findAllClients();
    }

}
