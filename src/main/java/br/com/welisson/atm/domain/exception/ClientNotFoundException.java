package br.com.welisson.atm.domain.exception;

import lombok.Getter;

@Getter
public class ClientNotFoundException extends ATMException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
