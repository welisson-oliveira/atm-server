package br.com.welisson.atm.domain;

import lombok.Getter;

@Getter
public class ATMException extends RuntimeException {
    private String message;

    public ATMException(String message) {
        this.message = message;
    }
}
