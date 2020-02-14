package com.solvd.bankomat.service.impl;

import com.solvd.bankomat.dao.AtmDao;
import com.solvd.bankomat.model.Atm;
import com.solvd.bankomat.model.BankNote;
import com.solvd.bankomat.service.AtmService;

import java.util.List;

public class AtmServiceImpl implements AtmService {

    private AtmDao atmDao;

    public AtmServiceImpl() {
        this.atmDao = new AtmDao();
    }

    @Override
    public Atm getAtmById(Long id) {
        return atmDao.getAtmById(id);
    }

    @Override
    public void removeBankNotes(List<BankNote> bankNotes, Long atmId) {
        atmDao.removeBankNotes(bankNotes, atmId);
    }

}
