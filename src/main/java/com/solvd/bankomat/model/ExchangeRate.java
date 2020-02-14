package com.solvd.bankomat.model;

import java.math.BigDecimal;

public class ExchangeRate {

    private Long id;
    private BigDecimal rate;
    private Currency currency;
    private Action action;

    public enum Action {
        PURCHASE, SALE
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
