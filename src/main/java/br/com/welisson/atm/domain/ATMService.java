package br.com.welisson.atm.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ATMService {

    @Autowired
    private ClientService clientService;

    public Money withdraw(final Long idClient, final Long value){
        return clientService.withdraw(idClient, value);
    }

    public Client login(final String username, final String password){
        return clientService.login(username, password);
    }

}
