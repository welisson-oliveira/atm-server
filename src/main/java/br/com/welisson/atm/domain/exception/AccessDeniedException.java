package br.com.welisson.atm.domain.exception;

import lombok.Getter;

@Getter
public class AccessDeniedException extends ATMException {

    public AccessDeniedException(String message) {
        super(message);
    }
}
