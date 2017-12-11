package br.com.welisson.atm.domain.client.withdrawhandler;

import br.com.welisson.atm.domain.client.Bill;
import br.com.welisson.atm.domain.client.Money;

public class Fifty extends SubtractMoney {

    @Override
    protected boolean shouldResolve(Long balance, Long value) {
        return balance >= value && value / Bill.FIFTY.getBill() >= 0;
    }

    @Override
    public Money resolve(SubtractMoney next, Long balance, Long value, Money money) {
        if (shouldResolve(balance, value)) {

            money.addFifty(subtractValue(balance, value, Bill.FIFTY));

            if(value == 0){
                return money;
            }
            return next.resolve(new Ten(), getBalance(), getValue(), money);
        }
        return next.resolve(new Ten(), balance, value, money);
    }
}
