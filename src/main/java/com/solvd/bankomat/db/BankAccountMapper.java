package com.solvd.bankomat.db;

import com.solvd.bankomat.model.BankAccount;

import java.math.BigDecimal;

public interface BankAccountMapper {

    BankAccount getByCardId(Long cardId);

    void updateAmountById(Long id, BigDecimal amount);

}
