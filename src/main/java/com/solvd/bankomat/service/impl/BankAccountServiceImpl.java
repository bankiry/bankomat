package com.solvd.bankomat.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.bankomat.dao.BankAccountDao;
import com.solvd.bankomat.dao.CardDao;
import com.solvd.bankomat.dao.TransactionDao;
import com.solvd.bankomat.exception.TransactionException;
import com.solvd.bankomat.model.Bank;
import com.solvd.bankomat.model.BankAccount;
import com.solvd.bankomat.model.Card;
import com.solvd.bankomat.model.Currency;
import com.solvd.bankomat.model.ExchangeRate;
import com.solvd.bankomat.model.Transaction;
import com.solvd.bankomat.service.BankAccountService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

import static com.solvd.bankomat.model.Transaction.Status.COMPLETED;

public class BankAccountServiceImpl implements BankAccountService {

    private static final Logger LOGGER = Logger.getLogger(BankAccountServiceImpl.class);

    private CardDao cardDao;
    private BankAccountDao bankAccountDao;
    private TransactionDao transactionDao;
    private ObjectMapper objectMapper;

    public BankAccountServiceImpl() {
        this.cardDao = new CardDao();
        this.bankAccountDao = new BankAccountDao();
        this.transactionDao = new TransactionDao();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public BigDecimal getBalanceByCardInfo(String cardJson) throws TransactionException {
        Card cardFromJson = null;
        try {
            cardFromJson = objectMapper.readValue(cardJson, Card.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Cannot read card json into card object");
        }

        Card card = cardDao.getBySecurityInfo(cardFromJson.getNumber(), cardFromJson.getCardHolderName(), cardFromJson.getCvv());
        verifySecurityInfo(cardFromJson.getPin(), card);

        BankAccount bankAccount = bankAccountDao.getByCardId(card.getId());
        return bankAccount.getAmount();
    }

    @Override
    public void withdraw(String transactionJson) throws TransactionException {

        Transaction transaction = null;
        try {
            transaction = objectMapper.readValue(transactionJson, Transaction.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Cannot read transaction json into transaction object");
        }

        Card card = transaction.getCard();
        Card dbCard = cardDao.getBySecurityInfo(card.getNumber(), card.getCardHolderName(), card.getCvv());
        verifySecurityInfo(transaction.getCard().getPin(), dbCard);

        BankAccount bankAccount = bankAccountDao.getByCardId(dbCard.getId());

        int currentTransactionCounter = bankAccount.getTransactionCounter();

        if (transaction.getAmount().intValue() < 0) {
            throw new TransactionException("Transaction amount must be positive");
        }

        BigDecimal bankAccountConvertedAmount = convertAmount(bankAccount, transaction);
        if (bankAccount.getAmount().compareTo(bankAccountConvertedAmount) < 0) {
            throw new TransactionException("Bank account has not needed money");
        }

        BigDecimal amountAfterWithdrawal = bankAccount.getAmount().subtract(bankAccountConvertedAmount);

        int affectedRowsCount = bankAccountDao.updateAmountById(bankAccount.getId(), amountAfterWithdrawal, currentTransactionCounter);
        if (affectedRowsCount == 0) {
            throw new TransactionException("Operation is not done. Please try again.");
        }

        transaction.setCard(dbCard);
        transaction.setStatus(COMPLETED);
        transactionDao.create(transaction);
    }

    private BigDecimal convertAmount(BankAccount bankAccount, Transaction transaction) {
        Bank bank = bankAccount.getBank();
        Currency bankDefaultCurrency = bank.getDefaultCurrency();

        BigDecimal bankAccountConvertedAmount;

        if (!transaction.getCurrency().equals(bankAccount.getCurrency())) {
            ExchangeRate purchaseRate = getBankPurchaseRateByCurrency(bank, transaction.getCurrency());
            BigDecimal defaultBankCurrencyAmount = transaction.getAmount().multiply(purchaseRate.getRate());
            ExchangeRate saleRate = getBankSaleRateByCurrency(bank, bankAccount.getCurrency());

            bankAccountConvertedAmount = defaultBankCurrencyAmount.multiply(saleRate.getRate());
        } else {
            bankAccountConvertedAmount = transaction.getAmount();
        }
        return bankAccountConvertedAmount;
    }

    private ExchangeRate getBankPurchaseRateByCurrency(Bank bank, Currency transactionCurrency) {
        ExchangeRate purchaseRate = null;
        for (ExchangeRate exchangeRate : bank.getExchangeRates()) {
            if (exchangeRate.getCurrency().equals(transactionCurrency) && exchangeRate.getAction().equals(ExchangeRate.Action.PURCHASE)) {
                purchaseRate = exchangeRate;
                break;
            }
        }
        return purchaseRate;
    }

    private ExchangeRate getBankSaleRateByCurrency(Bank bank, Currency bankAccountCurrency) {
        ExchangeRate saleRate = null;
        for (ExchangeRate exchangeRate : bank.getExchangeRates()) {
            if (exchangeRate.getCurrency().equals(bankAccountCurrency) && exchangeRate.getAction().equals(ExchangeRate.Action.SALE)) {
                saleRate = exchangeRate;
                break;
            }
        }
        return saleRate;
    }

    private void verifySecurityInfo(Integer pinToCheck, Card card) throws TransactionException {
        if (card == null) {
            LOGGER.info("Card does not exist");
            throw new TransactionException("Card does not exist");
        }

        if (card.isBlocked()) {
            LOGGER.info("Card with id " + card.getId() + " is blocked");
            throw new TransactionException("Card is blocked");
        }

        if (!pinToCheck.equals(card.getPin())) {
            LOGGER.info("Card with id " + card.getId() + " has invalid pin");
            onInvalidPin(card);
            throw new TransactionException("Pin is invalid");
        }

        if (pinToCheck.equals(card.getPin()) && card.getPinAttemptsCount() < 3) {
            resetCardAttemptsToDefault(card);
        }
    }

    private void onInvalidPin(Card card) {
        int currentAttemptsCount = card.getPinAttemptsCount();
        card.setPinAttemptsCount(currentAttemptsCount - 1);

        if (currentAttemptsCount == 0) {
            LOGGER.info("Card with id " + card.getId() + " attempts count is 0. We are blocking this card");
            card.setBlocked(true);
        }
        cardDao.updateCard(card);
    }

    private void resetCardAttemptsToDefault(Card card) {
        card.setPinAttemptsCount(3);
        cardDao.updateCard(card);
    }
}
