package br.com.welisson.atm.domain.exception;

import lombok.Getter;

@Getter
public class AccountAlreadyExistsException extends ATMException {

    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
