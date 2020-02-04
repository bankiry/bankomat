package com.solvd.bankomat.model;

import java.util.List;

public class Bank {

    private long id;
    private String name;
    private int departmentNumber;
    private List<BankCustomer> bankCustomers;
    private List<ExchangeRate> exchangeRates;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(int departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public List<BankCustomer> getBankCustomers() {
        return bankCustomers;
    }

    public void setBankCustomers(List<BankCustomer> bankCustomers) {
        this.bankCustomers = bankCustomers;
    }

    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(List<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }
}
