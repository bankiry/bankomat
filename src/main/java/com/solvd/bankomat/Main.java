package com.solvd.bankomat;

import com.solvd.bankomat.exception.TransactionException;
import com.solvd.bankomat.model.Atm;
import com.solvd.bankomat.service.AtmService;
import com.solvd.bankomat.service.impl.AtmServiceImpl;
import com.solvd.bankomat.service.util.AtmRunner;
import org.apache.log4j.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) throws TransactionException {

        Long atmId = 1L;

        LOGGER.debug("Atm with id " + atmId + " process is starting");

        AtmService atmService = new AtmServiceImpl();
        Atm atm = atmService.getAtmById(atmId);

        AtmRunner atmRunner = new AtmRunner();
        atmRunner.start(atm);

        LOGGER.debug("Atm with id " + atmId + " process finished");
    }
}
