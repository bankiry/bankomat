package com.solvd.bankomat.service;

import com.solvd.bankomat.model.Atm;
import com.solvd.bankomat.model.Transaction;

import java.math.BigDecimal;

public interface AtmService {

    Atm getAtmById(Long id);

    boolean hasNeededSum(BigDecimal amount);

    void withdraw(Transaction transaction);
}
