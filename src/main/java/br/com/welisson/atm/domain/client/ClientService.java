package br.com.welisson.atm.domain.client;

import br.com.welisson.atm.domain.exception.*;
import br.com.welisson.atm.domain.client.withdrawhandler.WithdrawHandler;
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
            throw new InsufficientBalanceException("Insufficient balance");
        }

        final Money money = new WithdrawHandler().resolve(client.getBalance(), value);
        client.subtractBalance(value);
        clientRepository.save(client);
        return money;

    }

    public Client getClient(final Long id){
        final Client client = clientRepository.findOne(id);
        if(client == null){
            throw new ClientNotFoundException("Client not found");
        }
        return client;
    }

    public Client login(final String account, final String password){
        Client client = clientRepository.getClientByAccountAndPassword(account, password);
        if(client == null){
            throw new AccessDeniedException("Invalid account e/or password");
        }
        return client;
    }

    public Client createClient(final Client client) {

        if(accountAlreadyExist(client.getLogin().getAccount())){
           throw new AccountAlreadyExistsException("Account already exists!");
        }

        return clientRepository.save(client);
    }

    public Client updateClient(final Client client){
        Client clientDb = getClient(client.getId());
        if(!client.sameBalance(clientDb.getBalance())){
            throw new ModifiedBalanceException("Balance can not be changed");
        }

        if(accountAlreadyExist(client)){
            throw new AccountAlreadyExistsException("Account already exists");
        }

        return clientRepository.save(client);

    }

    private boolean accountAlreadyExist(final Client client){
        Client clientByAccount = clientRepository.findClientByAccount(client.getLogin().getAccount(), client.getId());
        return clientByAccount != null;
    }

    private boolean accountAlreadyExist(final String account){
        Client clientByAccount = clientRepository.findClientByAccount(account);
        return clientByAccount != null;
    }

    public void deleteClient(final Long id) {
        final Client clientToDelete = getClient(id);
        clientRepository.delete(clientToDelete);
    }

    public List<Client> findAllClients() {
        return (List<Client>) clientRepository.findAll();
    }
}
