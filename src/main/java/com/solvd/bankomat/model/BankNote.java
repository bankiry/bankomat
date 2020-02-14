package com.solvd.bankomat.model;

public class BankNote implements Comparable<BankNote> {

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
    public int compareTo(BankNote bankNote) {
        return this.denomination < bankNote.denomination ? -1 : this.denomination.equals(bankNote.denomination) ? 0 : 1;
    }
}
