package br.com.welisson.atm.domain.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WithdrawVO {
    private Long idClient;
    private Long value;
}
