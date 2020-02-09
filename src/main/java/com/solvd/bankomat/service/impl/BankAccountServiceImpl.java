package com.solvd.bankomat.service.impl;

import com.solvd.bankomat.db.BankAccountMapper;
import com.solvd.bankomat.db.CardMapper;
import com.solvd.bankomat.db.TransactionMapper;
import com.solvd.bankomat.model.Bank;
import com.solvd.bankomat.model.BankAccount;
import com.solvd.bankomat.model.Card;
import com.solvd.bankomat.model.Currency;
import com.solvd.bankomat.model.ExchangeRate;
import com.solvd.bankomat.model.Transaction;
import com.solvd.bankomat.service.BankAccountService;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BankAccountServiceImpl implements BankAccountService {

    private CardMapper cardMapper;
    private BankAccountMapper bankAccountMapper;
    private TransactionMapper transactionMapper;

    public BankAccountServiceImpl() {
        // TODO: 2/9/20 update according db mybatis implementation
        this.cardMapper = null;
        this.bankAccountMapper = null;
        this.transactionMapper = null;
    }

    @Override
    public BigDecimal getBalanceByCardInfo(Integer pin, BigInteger cardNumber, String cardholderName, Integer cvv) {
        Card card = cardMapper.getBySecurityInfo(cardNumber, cardholderName, cvv);
        verifySecurityInfo(pin, card);

        BankAccount bankAccount = bankAccountMapper.getByCardId(card.getId());
        return bankAccount.getAmount();
    }

    @Override
    public void withdraw(Transaction transaction) {
        Card card = transaction.getCard();
        Card dbCard = cardMapper.getBySecurityInfo(card.getNumber(), card.getCardHolderName(), card.getCvv());
        verifySecurityInfo(transaction.getCard().getPin(), dbCard);

        BankAccount bankAccount = bankAccountMapper.getByCardId(card.getId());

        // TODO: 2/9/20 add transactional safety

        if (transaction.getAmount().compareTo(new BigDecimal(0)) < 0) {
            // TODO: 2/9/20 throw an exception than transaction amount must be positive
        }

        Bank bank = bankAccount.getBank();
        Currency bankDefaultCurrency = bank.getDefaultCurrency();

        BigDecimal bankAccountConvertedAmount = null;

        if (!transaction.getCurrency().equals(bankAccount.getCurrency())) {
            ExchangeRate purchaseRate = null;
            for (ExchangeRate exchangeRate : bank.getExchangeRates()) {
                if (exchangeRate.getCurrency().equals(transaction.getCurrency()) && exchangeRate.getAction().equals(ExchangeRate.Action.PURCHASE)) {
                    purchaseRate = exchangeRate;
                    break;
                }
            }

            BigDecimal defaultBankCurrencyAmount = transaction.getAmount().multiply(purchaseRate.getRate());

            ExchangeRate saleRate = null;
            for (ExchangeRate exchangeRate : bank.getExchangeRates()) {
                if (exchangeRate.getCurrency().equals(bankAccount.getCurrency()) && exchangeRate.getAction().equals(ExchangeRate.Action.SALE)) {
                    saleRate = exchangeRate;
                    break;
                }
            }

            bankAccountConvertedAmount = defaultBankCurrencyAmount.multiply(saleRate.getRate());
        } else {
            bankAccountConvertedAmount = transaction.getAmount();
        }

        if (bankAccount.getAmount().compareTo(bankAccountConvertedAmount) < 0) {
            // TODO: 2/9/20 bank account sum less than requested amount, throw an exception
        }

        BigDecimal amountAfterWithdrawal = bankAccount.getAmount().subtract(bankAccountConvertedAmount);
        bankAccountMapper.updateAmountById(bankAccount.getId(), amountAfterWithdrawal);

        transactionMapper.create(transaction);

        // TODO: 2/9/20 remove transactional safety
    }

    private void verifySecurityInfo(Integer pinToCheck, Card card) {
        if (card == null) {
            // TODO: 2/9/20 throw card not exists exception
        }

        if (!pinToCheck.equals(card.getPin())) {
            // TODO: 2/9/20 implement logic connected with three pin attempts
        }

        if (card.isBlocked()) {
            // TODO: 2/9/20 thow card is blocked exception
        }
    }
}
