package com.solvd.bankomat.model;

import java.util.Date;

public class Card {

    private Long id;
    private Long number;
    private String cardHolderName;
    private Integer cvv;
    private Date expirationDate;

    private Integer pin;
    private Integer pinAttemptsCount;

    private boolean isBlocked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public Integer getPinAttemptsCount() {
        return pinAttemptsCount;
    }

    public void setPinAttemptsCount(Integer pinAttemptsCount) {
        this.pinAttemptsCount = pinAttemptsCount;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
