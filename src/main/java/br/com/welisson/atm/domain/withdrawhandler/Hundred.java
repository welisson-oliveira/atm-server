package br.com.welisson.atm.domain.withdrawhandler;

import br.com.welisson.atm.domain.Bill;
import br.com.welisson.atm.domain.Money;
import org.springframework.beans.factory.annotation.Autowired;

public class Hundred extends SubtractMoney {

    @Autowired
    private Money money;

    @Override
    protected boolean shouldResolve(Long balance, Long value) {
        return balance >= value && value / Bill.HUNDRED.getBill() >= 0;
    }

    @Override
    public Money resolve(SubtractMoney next, Long balance, Long value, final Money money) {
        if(money != null) this.money = money;

        if(shouldResolve(balance, value)){

            int cedules = 0;
            while(balance >= value && value >= Bill.HUNDRED.getBill() && value > 0) {
                balance -= Bill.HUNDRED.getBill();
                value -= Bill.HUNDRED.getBill();
                cedules++;
            }

            money.addHundred(cedules);
            if(value == 0){
                return money;
            }
        }
        return next.resolve(new Twenty(), balance, value, this.money);
    }
}
