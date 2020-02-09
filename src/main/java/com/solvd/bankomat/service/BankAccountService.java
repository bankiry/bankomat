package com.solvd.bankomat.service;

import com.solvd.bankomat.exception.TransactionException;

import java.math.BigDecimal;

public interface BankAccountService {

    BigDecimal getBalanceByCardInfo(String cardJson) throws TransactionException;

    void withdraw(String transactionJson) throws TransactionException;
}
