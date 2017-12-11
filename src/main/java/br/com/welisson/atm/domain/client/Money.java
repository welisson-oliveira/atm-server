package br.com.welisson.atm.domain.client;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Money {

    private int hundred;
    private int fifty;
    private int twenty;
    private int ten;

    public void addHundred(final int qtd) {
        hundred += qtd;
    }

    public void addFifty(final int qtd) {
        fifty += qtd;
    }

    public void addTwenty(final int qtd) {
        twenty += qtd;
    }

    public void addTen(final int qtd) {
        ten += qtd;
    }

}
