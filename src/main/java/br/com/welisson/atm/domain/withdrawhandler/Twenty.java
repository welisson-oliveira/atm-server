package br.com.welisson.atm.domain.withdrawhandler;

import br.com.welisson.atm.domain.Bill;
import br.com.welisson.atm.domain.Money;

public class Twenty extends SubtractMoney {

    @Override
    protected boolean shouldResolve(Long balance, Long value) {
        return balance >= value && value / Bill.TWENTY.getBill() >= 0;
    }

    @Override
    public Money resolve(SubtractMoney next, Long balance, Long value, Money money) {
        if (shouldResolve(balance, value)) {

            int cedules = 0;
            while (balance >= value && value >= Bill.TWENTY.getBill() && value > 0) {
                balance -= Bill.TWENTY.getBill();
                value -= Bill.TWENTY.getBill();
                cedules++;
            }

            money.addTwenty(cedules);
            if(value == 0){
                return money;
            }
        }
        return next.resolve(new FinalCedule(),balance,value,money);
    }
}
