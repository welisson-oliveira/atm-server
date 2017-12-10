package br.com.welisson.atm.restapi;

import br.com.welisson.atm.domain.AbstractController;
import br.com.welisson.atm.domain.Client;
import br.com.welisson.atm.domain.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atm/client")
@AllArgsConstructor
public class ClientController extends AbstractController {

    private ClientService clientService;

    @PostMapping("/create")
    public void createClient(@RequestBody final Client client){
        clientService.createClient(client);
    }

    @PutMapping("/update")
    public void updateClient(@RequestBody final Client client){
        clientService.updateClient(client);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClient(@PathVariable("id") final Long id){
        clientService.deleteClient(id);
    }

    @GetMapping("/list")
    public List<Client> listClients(){
        return clientService.findAllClients();
    }

}
