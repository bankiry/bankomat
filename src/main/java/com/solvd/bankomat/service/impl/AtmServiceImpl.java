package com.solvd.bankomat.service.impl;

import com.solvd.bankomat.db.AtmMapper;
import com.solvd.bankomat.model.Atm;
import com.solvd.bankomat.model.Transaction;
import com.solvd.bankomat.service.AtmService;

import java.math.BigDecimal;

public class AtmServiceImpl implements AtmService {

    private AtmMapper atmMapper;

    public AtmServiceImpl() {
        // TODO: 2/8/20 change with real implementation
        this.atmMapper = null;
    }

    @Override
    public Atm getAtmById(Long id) {
        return atmMapper.getAtmById(id);
    }

    @Override
    public boolean hasNeededSum(BigDecimal amount) {
        return false;
    }

    @Override
    public void withdraw(Transaction transaction) {

    }
}
