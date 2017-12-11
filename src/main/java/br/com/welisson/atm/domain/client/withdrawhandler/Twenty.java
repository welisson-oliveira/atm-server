package br.com.welisson.atm.domain.client.withdrawhandler;

import br.com.welisson.atm.domain.client.Bill;
import br.com.welisson.atm.domain.client.Money;

public class Twenty extends SubtractMoney {

    @Override
    protected boolean shouldResolve(Long balance, Long value) {
        return balance >= value && value / Bill.TWENTY.getBill() >= 0;
    }

    @Override
    public Money resolve(SubtractMoney next, Long balance, Long value, Money money) {
        if (shouldResolve(balance, value)) {

            money.addTwenty(subtractValue(balance, value, Bill.TWENTY));
            if (value == 0) {
                return money;
            }

            return next.resolve(new FinalBallot(), getBalance(), getValue(), money);
        }
        return next.resolve(new FinalBallot(), balance, value, money);
    }
}
