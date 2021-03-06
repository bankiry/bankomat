package com.solvd.bankomat.model;

import java.math.BigDecimal;

public class Transaction {

    private Long id;
    private Status status;
    private Card card;

    private BigDecimal amount;
    private Currency currency;

    private Atm.Operation operation;

    public enum Status {
        PROCESSING, COMPLETED, FAILED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Atm.Operation getOperation() {
        return operation;
    }

    public void setOperation(Atm.Operation operation) {
        this.operation = operation;
    }
}
