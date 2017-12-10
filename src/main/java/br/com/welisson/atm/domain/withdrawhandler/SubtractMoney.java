package br.com.welisson.atm.domain.withdrawhandler;

import br.com.welisson.atm.domain.Money;

public abstract class SubtractMoney extends AbstractSubtractMoney {

    protected abstract boolean shouldResolve(final Long balance, final Long value);

    public abstract Money resolve(final SubtractMoney next, Long balance, final Long value, final Money money);

}
