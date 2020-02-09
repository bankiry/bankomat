package com.solvd.bankomat.db;

import com.solvd.bankomat.model.Transaction;

public interface TransactionMapper {

    void create(Transaction transaction);
}
