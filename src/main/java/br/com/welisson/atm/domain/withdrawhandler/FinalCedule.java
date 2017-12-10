package br.com.welisson.atm.domain.withdrawhandler;

import br.com.welisson.atm.domain.ATMException;
import br.com.welisson.atm.domain.Money;

public class FinalCedule extends SubtractMoney {

    @Override
    public boolean shouldResolve(Long balance, Long value) {
        return false;
    }

    @Override
    public Money resolve(SubtractMoney next, Long balance, Long value, Money money) {
        if(value == 0){
            return money;
        } else if(value > 0){
            throw new ATMException("Informe um valor multiplo de 10");
        }

        throw new ATMException("Impossivel sacar valor negativo");

    }
}
