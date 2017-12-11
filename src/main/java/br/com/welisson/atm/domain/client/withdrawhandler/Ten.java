package br.com.welisson.atm.domain.client.withdrawhandler;

import br.com.welisson.atm.domain.client.Bill;
import br.com.welisson.atm.domain.client.Money;

public class Ten extends SubtractMoney {

    @Override
    public boolean shouldResolve(Long balance, Long value) {
        return balance >= value && value / Bill.TEN.getBill() >= 0;
    }

    @Override
    public Money resolve(SubtractMoney next, Long balance, Long value, Money money) {
        if (shouldResolve(balance, value)) {

            money.addTen(subtractValue(balance, value, Bill.TEN));

            if(value == 0){
                return money;
            }

            return next.resolve(null, getBalance(), getValue(), money);
        }
        return next.resolve(null, balance, value, money);
    }
}
