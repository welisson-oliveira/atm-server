package br.com.welisson.atm.domain.exception;

import lombok.Getter;

@Getter
public class NegativeValueException extends ATMException {
    public NegativeValueException(String message) {
        super(message);
    }
}
