package br.com.welisson.atm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "CLIENT")
@AllArgsConstructor
@Getter
public class Client {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", length = 255, nullable = false)
    private String name;

    @OneToOne
    @Cascade({CascadeType.ALL})
    private Login login;

    @Column(name = "BALANCE")
    private Long balance;

    private Client(){}

    public boolean hasBalanceForWithdraw(final Long value){
        return value <= balance;
    }

    public void subtractBalance(final Long value){
        Long balanceTemp = 0L;
        if(hasBalanceForWithdraw(value)) {
            balanceTemp = this.balance - value;
        }
        if(balanceTemp < 0){
            throw new ATMException("O Cliente não pode ter saldo negativo");
        }

        this.balance = balanceTemp;

    }

    public boolean sameBalance(Long balance) {
        return this.balance.equals(balance);
    }

}
