package com.solvd.bankomat;

import com.solvd.bankomat.dao.AtmDao;
import com.solvd.bankomat.dao.BankDao;
import com.solvd.bankomat.dao.BanknoteDao;
import com.solvd.bankomat.dao.ExchangeRateDao;
import com.solvd.bankomat.model.Atm;
import com.solvd.bankomat.model.Bank;
import com.solvd.bankomat.model.BankNote;
import com.solvd.bankomat.model.Currency;
import com.solvd.bankomat.model.ExchangeRate;
import com.solvd.bankomat.service.AtmService;
import com.solvd.bankomat.service.impl.AtmServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AtmServiceTest {

    private AtmDao atmDao = new AtmDao();
    private BankDao bankDao = new BankDao();
    private ExchangeRateDao exchangeRateDao = new ExchangeRateDao();
    private BanknoteDao banknoteDao = new BanknoteDao();

    private Atm atm;

    @BeforeMethod
    public void before() {

        Bank bank = createBank();

        atm = new Atm();
        atm.setOwnerBank(bank);
        atm = atmDao.createAtm(atm);

        List<ExchangeRate> exchangeRates = createExchangeRates(bank.getId());
        atm.getOwnerBank().setExchangeRates(exchangeRates);

        List<BankNote> bankNotes = createBanknotes();
        attacheBanknotesToAtm(bankNotes);

        atm.setBankNotes(bankNotes);
    }

    @Test
    public void verifyGetAtmByIdTest() {
        AtmService atmService = new AtmServiceImpl();
        Atm atmFromDb = atmService.getAtmById(atm.getId());
        Assert.assertNotNull(atmFromDb, "Atm does not exist");
        Assert.assertNotNull(atmFromDb.getOwnerBank(), "Atm bank owner does not exist");

        Assert.assertEquals(atmFromDb.getOwnerBank().getDefaultCurrency(), atm.getOwnerBank().getDefaultCurrency(), "Bank default currencies don't match");
        Assert.assertEquals(atmFromDb.getOwnerBank().getName(), atm.getOwnerBank().getName(), "Bank names don't match");

        Assert.assertNotNull(atmFromDb.getId(), "Atm from db must have an id");
        Assert.assertNotNull(atmFromDb.getOwnerBank().getId(), "Bank from db must have an id");

        Assert.assertNotNull(atmFromDb.getOwnerBank().getExchangeRates(), "Owner bank must have exchange rates");
        Assert.assertEquals(atmFromDb.getOwnerBank().getExchangeRates().size(), atm.getOwnerBank().getExchangeRates().size(), "Incorrect count of exchange rates was found");
        for (ExchangeRate exchangeRate : atmFromDb.getOwnerBank().getExchangeRates()) {
            Assert.assertNotNull(exchangeRate.getId(), "Exchange rate id was not generated after insert");
        }

        for (BankNote bankNote : atmFromDb.getBankNotes()) {
            Assert.assertNotNull(bankNote.getId(), "Banknote is was not generated after insert");
        }
    }

    @AfterMethod
    public void after() {
        deleteExchangeRates();
        deleteAtmBanknotes();
        deleteAtm();
        deleteBank();
    }

    private Bank createBank() {
        Bank bank = new Bank();
        bank.setName("TestBank");
        bank.setDefaultCurrency(Currency.BYN);
        return bankDao.createBank(bank);
    }

    private List<ExchangeRate> createExchangeRates(Long bankId) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setRate(BigDecimal.valueOf(2));
        exchangeRate.setCurrency(Currency.USD);
        exchangeRate.setAction(ExchangeRate.Action.SALE);

        ExchangeRate exchangeRate2 = new ExchangeRate();
        exchangeRate2.setRate(BigDecimal.valueOf(2.5));
        exchangeRate2.setCurrency(Currency.USD);
        exchangeRate2.setAction(ExchangeRate.Action.PURCHASE);

        exchangeRate = exchangeRateDao.createExchangeRate(exchangeRate, bankId);
        exchangeRate2 = exchangeRateDao.createExchangeRate(exchangeRate2, bankId);

        List<ExchangeRate> exchangeRates = new ArrayList<>();
        exchangeRates.add(exchangeRate);
        exchangeRates.add(exchangeRate2);

        return exchangeRates;
    }

    private List<BankNote> createBanknotes() {
        BankNote bankNote = new BankNote();
        bankNote.setDenomination(2);
        bankNote.setCurrency(Currency.USD);

        BankNote bankNote2 = new BankNote();
        bankNote2.setDenomination(2);
        bankNote2.setCurrency(Currency.USD);

        bankNote = banknoteDao.createBanknote(bankNote);
        bankNote2 = banknoteDao.createBanknote(bankNote2);

        List<BankNote> bankNotes = new ArrayList<>();
        bankNotes.add(bankNote);
        bankNotes.add(bankNote2);

        return bankNotes;
    }

    private void attacheBanknotesToAtm(List<BankNote> bankNotes) {
        for (BankNote bankNote : bankNotes) {
            atmDao.addBanknote(bankNote.getId(), atm.getId());
        }
    }

    private void deleteExchangeRates() {
        for (ExchangeRate exchangeRate : atm.getOwnerBank().getExchangeRates()) {
            exchangeRateDao.removeById(exchangeRate.getId());
        }
    }

    private void deleteAtmBanknotes() {
        atmDao.removeBankNotes(atm.getBankNotes(), atm.getId());

        for (BankNote bankNote : atm.getBankNotes()) {
            banknoteDao.removeById(bankNote.getId());
        }
    }

    private void deleteAtm() {
        atmDao.removeById(atm.getId());
    }

    private void deleteBank() {
        bankDao.removeById(atm.getOwnerBank().getId());
    }
}
