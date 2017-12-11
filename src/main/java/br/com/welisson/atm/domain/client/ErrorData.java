package br.com.welisson.atm.domain.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorData {

    private final int errorCode;
    private final String message;
}