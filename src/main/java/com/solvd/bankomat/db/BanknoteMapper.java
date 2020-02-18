package com.solvd.bankomat.db;

import com.solvd.bankomat.model.BankNote;

public interface BanknoteMapper {

    void create(BankNote bankNote);

    void removeById(Long id);
}
