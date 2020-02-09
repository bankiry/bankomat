package com.solvd.bankomat.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.bankomat.exception.AtmException;
import com.solvd.bankomat.exception.TransactionException;
import com.solvd.bankomat.model.Atm;
import com.solvd.bankomat.model.BankNote;
import com.solvd.bankomat.model.Card;
import com.solvd.bankomat.model.Currency;
import com.solvd.bankomat.model.Transaction;
import com.solvd.bankomat.service.AtmService;
import com.solvd.bankomat.service.BankAccountService;
import com.solvd.bankomat.service.MultiLanguageInput;
import com.solvd.bankomat.service.impl.AtmServiceImpl;
import com.solvd.bankomat.service.impl.BankAccountServiceImpl;
import com.solvd.bankomat.service.impl.multilanguage.EnglishMultiLanguageInput;
import com.solvd.bankomat.service.impl.multilanguage.RussianMultiLanguageInput;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AtmRunner {

    private static final Logger LOGGER = Logger.getLogger(AtmRunner.class);

    private Scanner scanner = new Scanner(System.in);
    private MultiLanguageInput multiLanguageInput;
    private BankAccountService bankAccountService = new BankAccountServiceImpl();
    private AtmService atmService = new AtmServiceImpl();
    private ObjectMapper objectMapper = new ObjectMapper();

    public void start(Atm atm) throws TransactionException {

        this.multiLanguageInput = askLanguage(atm);

        BigInteger cardNumber = askCardNumber();
        String cardholderName = askCardholderName();
        Integer cvv = askCvv();

        Atm.Operation operation = askOperation(atm);
        Integer pin = askPin();

        switch (operation) {
            case WITHDRAWAL:
                onWithdrawal(atm, cardNumber, cardholderName, cvv, pin, operation);
                break;
            case VIEW_BALANCE:
                onViewBalance(cardNumber, cardholderName, cvv, pin);
                break;
            default:
                break;
        }
    }

    private void onWithdrawal(Atm atm, BigInteger cardNumber, String cardholderName, Integer cvv, Integer pin, Atm.Operation operation) throws TransactionException {
        Currency currency = askCurrency(atm);
        BigDecimal amount = askAmount();

        List<BankNote> currentCurrencyBankNotes = getAtmBanknotesByCurrency(atm, currency);

        List<BankNote> bankNotesToAdd = getBanknotesForWithdrawal(amount.intValue(), currentCurrencyBankNotes);

        Card card = new Card();
        card.setNumber(cardNumber);
        card.setCardHolderName(cardholderName);
        card.setCvv(cvv);
        card.setPin(pin);

        Transaction transaction = new Transaction();
        transaction.setCard(card);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setOperation(operation);

        String transactionAsString = null;
        try {
            transactionAsString = objectMapper.writeValueAsString(transaction);
        } catch (JsonProcessingException e) {
            LOGGER.error("Cannot unmarshal transaction into string");
        }

        bankAccountService.withdraw(transactionAsString);
        atmService.removeBankNotes(bankNotesToAdd);
    }

    private List<BankNote> getAtmBanknotesByCurrency(Atm atm, Currency currency) {
        List<BankNote> currentCurrencyBankNotes = new ArrayList<>();
        for(BankNote bankNote : atm.getBankNotes()) {
            if (bankNote.getCurrency().equals(currency)) {
                currentCurrencyBankNotes.add(bankNote);
            }
        }
        return currentCurrencyBankNotes;
    }

    private List<BankNote> getBanknotesForWithdrawal(Integer amount, List<BankNote> atmBanknotes) {
        List<BankNote> bankNotesToAdd = null;

        while (bankNotesToAdd == null) {
            try {
                bankNotesToAdd = AtmAlgorithm.run(amount, atmBanknotes);
            } catch (AtmException e) {
                System.out.println(multiLanguageInput.getAtmHasNotRequiredDenominationsMessage());
            }
        }
        return bankNotesToAdd;
    }

    private void onViewBalance(BigInteger cardNumber, String cardholderName, Integer cvv, Integer pin) throws TransactionException {
        Card card = new Card();
        card.setPin(pin);
        card.setNumber(cardNumber);
        card.setCardHolderName(cardholderName);
        card.setCvv(cvv);

        String cardAsString = null;
        try {
            cardAsString = objectMapper.writeValueAsString(card);
        } catch (JsonProcessingException e) {
            LOGGER.error("Cannot unmarshal card to json");
        }

        BigDecimal balance = bankAccountService.getBalanceByCardInfo(cardAsString);
        System.out.println(balance);
    }

    private MultiLanguageInput askLanguage(Atm atm) {
        System.out.println("Choose language:");

        List<Atm.Language> atmLanguages = atm.getLanguages();

        Atm.Language language = null;
        while(language == null) {
            for(int i = 1; i <= atmLanguages.size(); i++) {
                System.out.println("    " + atmLanguages.get(i - 1) + " (press " + i + ")");
            }

            int languageIndex = scanner.nextInt();
            language = atmLanguages.get(languageIndex - 1);

            if (language == null) {
                System.err.println("Sorry, this language does not supported. Try again.");
            }
        }

        LOGGER.debug("Current language is " + language.name());

        MultiLanguageInput multiLanguageInput = null;
        switch (language) {
            case RUSSIAN:
                multiLanguageInput = new RussianMultiLanguageInput();
                break;
            case ENGLISH:
                multiLanguageInput = new EnglishMultiLanguageInput();
                break;
            default:
                break;
        }

        return multiLanguageInput;
    }

    private BigInteger askCardNumber() {
        System.out.println(multiLanguageInput.getCardNumberMessage());
        return scanner.nextBigInteger();
    }

    private String askCardholderName() {
        System.out.println(multiLanguageInput.getCardholderNameMessage());
        return scanner.next();
    }

    private Integer askCvv() {
        System.out.println(multiLanguageInput.getCvvMessage());
        return scanner.nextInt();
    }

    private Atm.Operation askOperation(Atm atm) {
        System.out.println(multiLanguageInput.getChooseOperationMessage());

        List<Atm.Operation> atmOperations = atm.getOperations();
        Atm.Operation operation = null;
        while(operation == null) {
            for(int i = 1; i <= atmOperations.size(); i++) {
                String operationName = null;
                switch (atmOperations.get(i - 1)) {
                    case WITHDRAWAL:
                        operationName = multiLanguageInput.getWithdrawalMessage();
                        break;
                    case VIEW_BALANCE:
                        operationName = multiLanguageInput.getViewBalanceMessage();
                        break;
                    default:
                        break;
                }
                System.out.println("    " + operationName + " (" + multiLanguageInput.getPressMessage() + " " + i + ")");
            }

            int operationIndex = scanner.nextInt();
            operation = atmOperations.get(operationIndex - 1);

            if (operation == null) {
                System.err.println(multiLanguageInput.getOperationNotSupportedMessage());
            }
        }
        LOGGER.debug("Current operation is " + operation.name());
        return operation;
    }

    private Integer askPin() {
        System.out.println(multiLanguageInput.getEnterPinMessage());
        return scanner.nextInt();
    }

    private Currency askCurrency(Atm atm) {
        System.out.println(multiLanguageInput.getChooseCurrencyMessage());
        Set<Currency> currencies = new HashSet<>();
        for(BankNote bankNote : atm.getBankNotes()) {
            currencies.add(bankNote.getCurrency());
        }
        List<Currency> currencyList = new ArrayList<>(currencies);

        Currency currency = null;
        while(currency == null) {
            for(int i = 1; i <= currencyList.size(); i++) {
                System.out.println("    " + currencyList.get(i - 1) + " (" + multiLanguageInput.getPressMessage() + " " + i + ")");
            }

            int currencyIndex = scanner.nextInt();
            currency = currencyList.get(currencyIndex - 1);

            if (currency == null) {
                System.err.println(multiLanguageInput.getCurrencyNotExistMessage());
            }
        }
        LOGGER.debug("Current currency is " + currency.name());
        return currency;
    }

    private BigDecimal askAmount() {
        System.out.println(multiLanguageInput.getEnterAmountMessage());
        return scanner.nextBigDecimal();
    }

}
