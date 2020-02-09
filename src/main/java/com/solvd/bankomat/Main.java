package com.solvd.bankomat;

import com.solvd.bankomat.model.Atm;
import com.solvd.bankomat.service.AtmService;
import com.solvd.bankomat.service.impl.AtmServiceImpl;
import com.solvd.bankomat.service.util.AtmRunner;

public class Main {

    public static void main(String[] args) {

        Long atmId = 1L;

        AtmService atmService = new AtmServiceImpl();
        Atm atm = atmService.getAtmById(atmId);

        AtmRunner atmRunner = new AtmRunner();
        atmRunner.start(atm);
    }
}
