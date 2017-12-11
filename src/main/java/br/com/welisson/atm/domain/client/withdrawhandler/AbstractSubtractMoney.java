package br.com.welisson.atm.domain.client.withdrawhandler;

import br.com.welisson.atm.domain.client.Bill;
import lombok.Getter;

@Getter
public abstract class AbstractSubtractMoney {

    private Long balance;
    private Long value;

    protected int subtractValue(Long balance, Long value, Bill bill){

        int balloots = 0;
        while(balance >= value && value >= bill.getBill() && value > 0) {
            balance -= bill.getBill();
            value -= bill.getBill();
            balloots++;
        }

        this.balance = balance;
        this.value = value;

        return balloots;
    }

}
