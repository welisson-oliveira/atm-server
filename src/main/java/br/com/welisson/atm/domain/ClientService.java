package br.com.welisson.atm.domain;

import br.com.welisson.atm.domain.withdrawhandler.WithdrawHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Money withdraw(final Long idClient, final Long value) {

        final Client client = clientRepository.findOne(idClient);

        if(!client.hasBalanceForWithdraw(value)){
            throw new ATMException("Saldo insuficiente");
        }

        final Money money = new WithdrawHandler().resolve(client.getBalance(), value);
        client.subtractBalance(value);
        clientRepository.save(client);
        return money;

    }

    public Client getClient(final Long id){
        final Client client = clientRepository.findOne(id);
        if(client == null){
            throw new ATMException("Cliente não encontrado");
        }
        return client;
    }

    public Client login(final String account, final String password){
        Client client = clientRepository.getClientByAccountAndPassword(account, password);
        if(client == null){
            throw new ATMException("Conta e/ou senha inválida");
        }
        return client;
    }

    public Client createClient(final Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(final Client client){
        if(client.sameBalance(getClient(client.getId()).getBalance())){
            return clientRepository.save(client);
        }
        throw new ATMException("Saldo não pode ser alterado");
    }

    public void deleteClient(final Long id) {
        final Client clientToDelete = getClient(id);
        clientRepository.delete(clientToDelete);
    }

    public List<Client> findAllClients() {
        return (List<Client>) clientRepository.findAll();
    }
}
