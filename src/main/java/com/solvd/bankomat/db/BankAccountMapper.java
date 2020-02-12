package com.solvd.bankomat.db;

import com.solvd.bankomat.model.BankAccount;

import java.math.BigDecimal;

public interface BankAccountMapper {

    BankAccount getByCardId(Long cardId);

    // update bank_accounts set amount = {amount}, transaction_counter = {transactionCounter + 1} WHERE id = {id} AND transaction_counter = {transactionCounter};
    // mybatis returns affected rows count by default
    int updateAmountById(Long id, BigDecimal amount, Integer transactionCounter);

}
