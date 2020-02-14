package com.solvd.bankomat.model;

import java.util.List;

public class Atm {

    private Long id;
    private Bank ownerBank;
    private List<Language> languages;
    private List<BankNote> bankNotes;
    private List<Operation> operations;

    public enum Language {
        RUSSIAN, ENGLISH;
    }

    public enum Operation {
        WITHDRAWAL, VIEW_BALANCE
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bank getOwnerBank() {
        return ownerBank;
    }

    public void setOwnerBank(Bank ownerBank) {
        this.ownerBank = ownerBank;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<BankNote> getBankNotes() {
        return bankNotes;
    }

    public void setBankNotes(List<BankNote> bankNotes) {
        this.bankNotes = bankNotes;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

}
