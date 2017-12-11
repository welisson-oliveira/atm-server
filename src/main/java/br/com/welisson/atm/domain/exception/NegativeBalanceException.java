package br.com.welisson.atm.domain.exception;

import lombok.Getter;

@Getter
public class NegativeBalanceException extends ATMException {
    public NegativeBalanceException(String message) {
        super(message);
    }
}
