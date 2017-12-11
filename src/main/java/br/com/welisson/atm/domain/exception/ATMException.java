package br.com.welisson.atm.domain.exception;

import lombok.Getter;

@Getter
public abstract class ATMException extends RuntimeException {
    private String message;

    public ATMException(String message) {
        this.message = message;
    }
}
