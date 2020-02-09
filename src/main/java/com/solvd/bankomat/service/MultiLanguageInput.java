package com.solvd.bankomat.service;

public interface MultiLanguageInput {

    String getCardNumberMessage();

    String getCardholderNameMessage();

    String getCvvMessage();

    String getChooseOperationMessage();

    String getOperationNotSupportedMessage();

    String getPressMessage();

    String getEnterPinMessage();

    String getWithdrawalMessage();

    String getViewBalanceMessage();

    String getChooseCurrencyMessage();

    String getCurrencyNotExistMessage();

    String getEnterAmountMessage();

    String getAtmHasNotRequiredDenominationsMessage();
}
