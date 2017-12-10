package br.com.welisson.atm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorData {

    private final int errorCode;
    private final String message;
}