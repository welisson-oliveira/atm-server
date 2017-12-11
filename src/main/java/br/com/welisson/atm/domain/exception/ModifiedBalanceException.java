package br.com.welisson.atm.domain.exception;

import lombok.Getter;

@Getter
public class ModifiedBalanceException extends ATMException {
    public ModifiedBalanceException(String message) {
        super(message);
    }
}
