package com.solvd.bankomat.service;

import com.solvd.bankomat.model.Atm;
import com.solvd.bankomat.model.BankNote;

import java.util.List;

public interface AtmService {

    Atm getAtmById(Long id);

    void removeBankNotes(List<BankNote> bankNotes, Long atmId);
}
