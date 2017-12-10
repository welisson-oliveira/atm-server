package br.com.welisson.atm.domain;

public enum Bill {
    HUNDRED(100),
    FIFTY(50),
    TWENTY(20),
    TEN(10);

    private int value;

    Bill(int value){
        this.value = value;
    }

    public int getBill(){
        return value;
    }
}
