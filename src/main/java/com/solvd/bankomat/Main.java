package com.solvd.bankomat;

import com.solvd.bankomat.exception.TransactionException;
import com.solvd.bankomat.model.Atm;
import com.solvd.bankomat.model.Bank;
import com.solvd.bankomat.model.BankAccount;
import com.solvd.bankomat.model.BankCustomer;
import com.solvd.bankomat.model.BankNote;
import com.solvd.bankomat.model.Card;
import com.solvd.bankomat.model.Currency;
import com.solvd.bankomat.model.ExchangeRate;
import com.solvd.bankomat.service.AtmService;
import com.solvd.bankomat.service.impl.AtmServiceImpl;
import com.solvd.bankomat.service.util.AtmRunner;
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

    public static Atm createAtm() {
        List<ExchangeRate> exchangeRatesList = new ArrayList<>();
        List<Card> cardsList = new ArrayList<>();
        List<BankCustomer> bankCustomerList = new ArrayList<>();
        List<BankAccount> bankAccountsList = new ArrayList<>();
        List<BankNote> bankNoteList = new ArrayList<>();
        List<Atm.Language> languageList = new ArrayList<>();
        List<Atm.Operation> operationList = new ArrayList<>();

        Bank belarusbank = new Bank();
        belarusbank.setDefaultCurrency(Currency.BYN);
        belarusbank.setId(109L);

        ExchangeRate usdSaleRate = new ExchangeRate();
        ExchangeRate usdPurchaseRate = new ExchangeRate();
        ExchangeRate eurSaleRate = new ExchangeRate();
        ExchangeRate eurPurchaseRate = new ExchangeRate();

        usdSaleRate.setId(1L);
        usdSaleRate.setAction(ExchangeRate.Action.SALE);
        usdSaleRate.setCurrency(Currency.USD);
        BigDecimal usdSale = BigDecimal.valueOf((2.12));

        usdPurchaseRate.setId(2L);
        usdPurchaseRate.setAction(ExchangeRate.Action.PURCHASE);
        usdPurchaseRate.setCurrency(Currency.USD);
        BigDecimal usdPurchase = BigDecimal.valueOf((2.20));

        eurSaleRate.setId(3L);
        eurSaleRate.setAction(ExchangeRate.Action.SALE);
        eurSaleRate.setCurrency(Currency.EUR);
        BigDecimal eurSale = BigDecimal.valueOf((2.50));

        eurPurchaseRate.setId(4L);
        eurPurchaseRate.setAction(ExchangeRate.Action.PURCHASE);
        eurPurchaseRate.setCurrency(Currency.EUR);
        BigDecimal eurPurchase = BigDecimal.valueOf((2.35));

        usdSaleRate.setRate(usdSale);
        usdPurchaseRate.setRate(usdPurchase);
        eurSaleRate.setRate(eurSale);
        eurPurchaseRate.setRate(eurPurchase);

        exchangeRatesList.add(eurPurchaseRate);
        exchangeRatesList.add(eurSaleRate);
        exchangeRatesList.add(usdPurchaseRate);
        exchangeRatesList.add(usdSaleRate);

        belarusbank.setExchangeRates(exchangeRatesList);

        Card card1 = new Card();
        card1.setId(37529642L);
        card1.setNumber(1234567891011112L);
        card1.setCvv(586);
        card1.setCardHolderName("MAXIM MAXIMENKO");
        card1.setPin(4324);
        card1.setPinAttemptsCount(3);
        Date date1 = new GregorianCalendar(2021, Calendar.FEBRUARY, 11).getTime();
        card1.setExpirationDate(date1);
        card1.setBlocked(false);
        cardsList.add(card1);

        Card card2 = new Card();
        card2.setId(37529643L);
        card2.setNumber(1234567891011113L);
        card2.setCvv(386);
        card2.setCardHolderName("ALEH CHAIKO");
        card2.setPin(4325);
        card2.setPinAttemptsCount(3);
        Date date2 = new GregorianCalendar(2022, Calendar.MARCH, 18).getTime();
        card2.setExpirationDate(date2);
        card2.setBlocked(false);
        cardsList.add(card2);

        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId(1001L);
        bankAccount1.setNumber("a375s23");
        bankAccount1.setBank(belarusbank);
        bankAccount1.setCards(cardsList);
        bankAccount1.setCurrency(Currency.BYN);
        bankAccount1.setTransactionCounter(0);
        BigDecimal amount1 = BigDecimal.valueOf((1000.00));
        bankAccount1.setAmount(amount1);
        bankAccountsList.add(bankAccount1);

        BankCustomer maximMaximenko = new BankCustomer();
        maximMaximenko.setId(1L);
        maximMaximenko.setBankAccounts(bankAccountsList);
        bankCustomerList.add(maximMaximenko);

        BankNote byn5 = new BankNote();
        byn5.setId(1L);
        byn5.setCurrency(Currency.BYN);
        byn5.setDenomination(5);
        BankNote byn10 = new BankNote();
        bankNoteList.add(byn5);

        byn5.setId(2L);
        byn5.setCurrency(Currency.BYN);
        byn5.setDenomination(10);
        bankNoteList.add(byn10);

        BankNote byn20 = new BankNote();
        byn5.setId(3L);
        byn5.setCurrency(Currency.BYN);
        byn5.setDenomination(20);
        bankNoteList.add(byn20);

        BankNote byn50 = new BankNote();
        byn5.setId(4L);
        byn5.setCurrency(Currency.BYN);
        byn5.setDenomination(50);
        bankNoteList.add(byn50);

        BankNote byn100 = new BankNote();
        byn5.setId(5L);
        byn5.setCurrency(Currency.BYN);
        byn5.setDenomination(100);
        bankNoteList.add(byn100);

        BankNote byn200 = new BankNote();
        byn5.setId(6L);
        byn5.setCurrency(Currency.BYN);
        byn5.setDenomination(200);
        bankNoteList.add(byn200);

        BankNote byn500 = new BankNote();
        byn5.setId(7L);
        byn5.setCurrency(Currency.BYN);
        byn5.setDenomination(500);
        bankNoteList.add(byn500);

        BankNote usd5 = new BankNote();
        byn5.setId(21L);
        byn5.setCurrency(Currency.USD);
        byn5.setDenomination(5);
        bankNoteList.add(usd5);

        BankNote usd10 = new BankNote();
        byn5.setId(22L);
        byn5.setCurrency(Currency.USD);
        byn5.setDenomination(10);
        bankNoteList.add(usd10);

        BankNote usd20 = new BankNote();
        byn5.setId(23L);
        byn5.setCurrency(Currency.USD);
        byn5.setDenomination(20);
        bankNoteList.add(usd20);

        BankNote usd50 = new BankNote();
        byn5.setId(24L);
        byn5.setCurrency(Currency.USD);
        byn5.setDenomination(50);
        bankNoteList.add(usd50);

        BankNote usd100 = new BankNote();
        byn5.setId(25L);
        byn5.setCurrency(Currency.USD);
        byn5.setDenomination(100);
        bankNoteList.add(usd100);

        BankNote eur10 = new BankNote();
        byn5.setId(41L);
        byn5.setCurrency(Currency.EUR);
        byn5.setDenomination(10);
        bankNoteList.add(eur10);

        BankNote eur20 = new BankNote();
        byn5.setId(42L);
        byn5.setCurrency(Currency.EUR);
        byn5.setDenomination(20);
        bankNoteList.add(eur20);


        BankNote eur50 = new BankNote();
        byn5.setId(43L);
        byn5.setCurrency(Currency.EUR);
        byn5.setDenomination(50);
        bankNoteList.add(eur50);

        BankNote eur100 = new BankNote();
        byn5.setId(44L);
        byn5.setCurrency(Currency.EUR);
        byn5.setDenomination(100);
        bankNoteList.add(eur100);

        BankNote eur200 = new BankNote();
        byn5.setId(45L);
        byn5.setCurrency(Currency.EUR);
        byn5.setDenomination(200);
        bankNoteList.add(eur200);

        BankNote eur500 = new BankNote();
        byn5.setId(46L);
        byn5.setCurrency(Currency.EUR);
        byn5.setDenomination(500);
        bankNoteList.add(eur500);

        operationList.add(Atm.Operation.WITHDRAWAL);
        operationList.add(Atm.Operation.VIEW_BALANCE);

        languageList.add(Atm.Language.ENGLISH);
        languageList.add(Atm.Language.RUSSIAN);

        Atm bankomat = new Atm();
        bankomat.setId(1L);
        bankomat.setBankNotes(bankNoteList);
        bankomat.setLanguages(languageList);
        bankomat.setOperations(operationList);
        bankomat.setOwnerBank(belarusbank);

        return bankomat;
    }
}
