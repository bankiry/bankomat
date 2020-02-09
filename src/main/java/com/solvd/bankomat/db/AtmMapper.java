package com.solvd.bankomat.db;

import com.solvd.bankomat.model.Atm;
import com.solvd.bankomat.model.BankNote;

import java.util.List;

public interface AtmMapper {

    Atm getAtmById(Long id);

    void removeBankNotes(List<BankNote> bankNotes);
}
