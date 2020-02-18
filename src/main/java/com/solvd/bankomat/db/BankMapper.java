package com.solvd.bankomat.db;

import com.solvd.bankomat.model.Bank;

public interface BankMapper {

    void create(Bank bank);

    void removeById(Long id);
}
