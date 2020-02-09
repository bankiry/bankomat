package com.solvd.bankomat.service.impl.multilanguage;

import com.solvd.bankomat.service.MultiLanguageInput;

public class RussianMultiLanguageInput implements MultiLanguageInput {

    @Override
    public String getCardNumberMessage() {
        return "Введите номер карты:";
    }

    @Override
    public String getCardholderNameMessage() {
        return "Введите имя обладателя карты";
    }

    @Override
    public String getCvvMessage() {
        return "Введите секретный номер карты (cvv)";
    }

    @Override
    public String getChooseOperationMessage() {
        return "Выберите операцию";
    }

    @Override
    public String getOperationNotSupportedMessage() {
        return "Извините, данная операция не поддерживается. Попробуйте еще раз.";
    }

    @Override
    public String getPressMessage() {
        return "нажмите";
    }

    @Override
    public String getEnterPinMessage() {
        return "Введите пин код";
    }

    @Override
    public String getWithdrawalMessage() {
        return "Снятие наличных";
    }

    @Override
    public String getViewBalanceMessage() {
        return "Посмотреть баланс";
    }

    @Override
    public String getChooseCurrencyMessage() {
        return "Выберите валюту";
    }

    @Override
    public String getCurrencyNotExistMessage() {
        return "Извините, данной валюты не существует. Попробуйте еще раз.";
    }

    @Override
    public String getEnterAmountMessage() {
        return "Введите сумму";
    }

    @Override
    public String getAtmHasNotRequiredDenominationsMessage() {
        return "Введите другую сумму, пожалуйста. В банкомате нету необходимых купюр.";
    }
}
