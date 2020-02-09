package com.solvd.bankomat.model;

import java.util.Comparator;

public class BankNote implements Comparator<BankNote> {

    private Long id;
    private Integer denomination;
    private Currency currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDenomination() {
        return denomination;
    }

    public void setDenomination(Integer denomination) {
        this.denomination = denomination;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public int compare(BankNote b1, BankNote b2) {
        return b1.denomination < b2.denomination ? -1 : b1.denomination.equals(b2.denomination) ? 0 : 1;
    }
}
