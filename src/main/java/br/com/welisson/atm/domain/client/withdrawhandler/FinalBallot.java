package br.com.welisson.atm.domain.client.withdrawhandler;

import br.com.welisson.atm.domain.client.Money;
import br.com.welisson.atm.domain.exception.NegativeValueException;
import br.com.welisson.atm.domain.exception.NotMultipleOfTenException;

public class FinalBallot extends SubtractMoney {

    @Override
    public boolean shouldResolve(Long balance, Long value) {
        return false;
    }

    @Override
    public Money resolve(SubtractMoney next, Long balance, Long value, Money money) {
        if(value == 0){
            return money;
        } else if(value > 0){
            throw new NotMultipleOfTenException("value is not a multiple of 10");
        }

        throw new NegativeValueException("Impossible to draw negative");

    }
}
