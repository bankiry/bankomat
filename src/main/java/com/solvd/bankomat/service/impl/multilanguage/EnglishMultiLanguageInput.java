package com.solvd.bankomat.service.impl.multilanguage;

import com.solvd.bankomat.service.MultiLanguageInput;

public class EnglishMultiLanguageInput implements MultiLanguageInput {

    @Override
    public String getCardNumberMessage() {
        return "Enter card number";
    }

    @Override
    public String getCardholderNameMessage() {
        return "Enter cardholder name";
    }

    @Override
    public String getCvvMessage() {
        return "Enter cvv";
    }

    @Override
    public String getChooseOperationMessage() {
        return "Choose operation";
    }

    @Override
    public String getOperationNotSupportedMessage() {
        return "Sorry, this operation does not supported. Try again.";
    }

    @Override
    public String getPressMessage() {
        return "press";
    }

    @Override
    public String getEnterPinMessage() {
        return "Enter pin";
    }

    @Override
    public String getWithdrawalMessage() {
        return "Withdrawal";
    }

    @Override
    public String getViewBalanceMessage() {
        return "View balance";
    }

    @Override
    public String getChooseCurrencyMessage() {
        return "Choose currency";
    }

    @Override
    public String getCurrencyNotExistMessage() {
        return "Sorry, this currency is not existing now. Try again.";
    }

    @Override
    public String getEnterAmountMessage() {
        return "Enter an amount";
    }

    @Override
    public String getAtmHasNotRequiredDenominationsMessage() {
        return "PLease, try to enter another sum. Atm has not required denominations.";
    }
}
