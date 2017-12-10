package br.com.welisson.atm.domain.withdrawhandler;

import br.com.welisson.atm.domain.Money;

public class WithdrawHandler {

    public Money resolve(Long balance, Long value) {
        return new Hundred().resolve(new Fifty(), balance, value, new Money());
    }
}
