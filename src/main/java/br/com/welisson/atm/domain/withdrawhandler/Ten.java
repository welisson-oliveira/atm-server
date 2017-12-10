package br.com.welisson.atm.domain.withdrawhandler;

import br.com.welisson.atm.domain.Bill;
import br.com.welisson.atm.domain.Money;

public class Ten extends SubtractMoney {

    @Override
    public boolean shouldResolve(Long balance, Long value) {
        return balance >= value && value / Bill.TEN.getBill() >= 0;
    }

    @Override
    public Money resolve(SubtractMoney next, Long balance, Long value, Money money) {
        if (shouldResolve(balance, value)) {

            int cedules = 0;
            while (balance >= value && value >= Bill.TEN.getBill() && value > 0) {
                balance -= Bill.TEN.getBill();
                value -= Bill.TEN.getBill();
                cedules++;
            }

            money.addTen(cedules);
            if(value == 0){
                return money;
            }
        }
        return next.resolve(null, balance, value, money);
    }
}
