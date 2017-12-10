package br.com.welisson.atm.domain.withdrawhandler;

import br.com.welisson.atm.domain.Bill;

public abstract class AbstractSubtractMoney {

    public int subtractValue(Long balance, Long value, Bill bill){
        int cedules = 0;
        Long tempValue = value;
        while(balance >= tempValue) {
            balance -= bill.getBill();
            value -= bill.getBill();
            cedules++;
        }

        return cedules;
    }

}
