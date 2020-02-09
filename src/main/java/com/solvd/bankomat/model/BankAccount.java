package com.solvd.bankomat.model;

import java.math.BigDecimal;
import java.util.List;

public class BankAccount {

    private Long id;
    private String number;
    private List<Card> cards;

    private BigDecimal amount;
    private Currency currency;
    private Bank bank;

    private Integer transactionCounter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
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

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Integer getTransactionCounter() {
        return transactionCounter;
    }

    public void setTransactionCounter(Integer transactionCounter) {
        this.transactionCounter = transactionCounter;
    }
}
