package com.solvd.bankomat.service;

import com.solvd.bankomat.model.Transaction;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface BankAccountService {

    BigDecimal getBalanceByCardInfo(Integer pin, BigInteger cardNumber, String cardholderName, Integer cvv);

    void withdraw(Transaction transaction);
}
