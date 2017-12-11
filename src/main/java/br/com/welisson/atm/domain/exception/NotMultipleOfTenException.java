package br.com.welisson.atm.domain.exception;

import lombok.Getter;

@Getter
public class NotMultipleOfTenException extends ATMException {
    public NotMultipleOfTenException(String message) {
        super(message);
    }
}
