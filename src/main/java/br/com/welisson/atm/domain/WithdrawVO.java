package br.com.welisson.atm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WithdrawVO {
    private Long idClient;
    private Long value;
}
