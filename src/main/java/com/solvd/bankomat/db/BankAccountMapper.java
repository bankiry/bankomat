package com.solvd.bankomat.db;

import com.solvd.bankomat.model.BankAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface BankAccountMapper {

    BankAccount getByCardId(Long cardId);

    int updateAmountById(@Param("id") Long id, @Param("amount") BigDecimal amount, @Param("transactionCounter") Integer transactionCounter);

}
