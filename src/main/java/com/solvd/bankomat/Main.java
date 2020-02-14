package com.solvd.bankomat;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
