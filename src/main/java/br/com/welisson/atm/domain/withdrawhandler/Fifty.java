package br.com.welisson.atm.domain.withdrawhandler;

import br.com.welisson.atm.domain.Bill;
import br.com.welisson.atm.domain.Money;

public class Fifty extends SubtractMoney {

    @Override
    protected boolean shouldResolve(Long balance, Long value) {
        return balance >= value && value / Bill.FIFTY.getBill() >= 0;
    }

    @Override
    public Money resolve(SubtractMoney next, Long balance, Long value, Money money) {
        if (shouldResolve(balance, value)) {

            int cedules = 0;
            while (balance >= value && value >= Bill.FIFTY.getBill() && value > 0) {
                balance -= Bill.FIFTY.getBill();
                value -= Bill.FIFTY.getBill();
                cedules++;
            }

            money.addFifty(cedules);
            if(value == 0){
                return money;
            }
        }
        return next.resolve(new Ten(), balance, value, money);
    }
}
