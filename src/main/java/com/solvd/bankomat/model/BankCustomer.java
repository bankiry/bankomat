package com.solvd.bankomat.model;

import java.util.List;

public class BankCustomer {

    private long id;
    private List<BankAccount> bankAccounts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }
}
