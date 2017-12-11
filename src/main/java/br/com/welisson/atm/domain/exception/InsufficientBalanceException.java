package br.com.welisson.atm.domain.exception;

import lombok.Getter;

@Getter
public class InsufficientBalanceException extends ATMException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
