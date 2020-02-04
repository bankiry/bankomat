package com.solvd.bankomat.model;

import java.util.Date;

public class Card {

    private long id;
    private int number;
    private String cartHolderName;
    private int cvv;
    private Date expirationDate;

    private int pin;
    private int pinAttemptsCount;

    private Date releaseDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCartHolderName() {
        return cartHolderName;
    }

    public void setCartHolderName(String cartHolderName) {
        this.cartHolderName = cartHolderName;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getPinAttemptsCount() {
        return pinAttemptsCount;
    }

    public void setPinAttemptsCount(int pinAttemptsCount) {
        this.pinAttemptsCount = pinAttemptsCount;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
