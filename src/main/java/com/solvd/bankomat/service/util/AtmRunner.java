package com.solvd.bankomat.service.util;

import com.solvd.bankomat.model.Atm;
import com.solvd.bankomat.model.BankNote;
import com.solvd.bankomat.model.Card;
import com.solvd.bankomat.model.Currency;
import com.solvd.bankomat.model.Transaction;
import com.solvd.bankomat.service.BankAccountService;
import com.solvd.bankomat.service.MultiLanguageInput;
import com.solvd.bankomat.service.impl.BankAccountServiceImpl;
import com.solvd.bankomat.service.impl.multilanguage.EnglishMultiLanguageInput;
import com.solvd.bankomat.service.impl.multilanguage.RussianMultiLanguageInput;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AtmRunner {

    private Scanner scanner = new Scanner(System.in);
    private MultiLanguageInput multiLanguageInput;
    private BankAccountService bankAccountService = new BankAccountServiceImpl();

    public void start(Atm atm) {

        this.multiLanguageInput = askLanguage(atm);

        BigInteger cardNumber = askCardNumber();
        String cardholderName = askCardholderName();
        Integer cvv = askCvv();

        Atm.Operation operation = askOperation(atm);

        Integer pin = askPin();

        switch (operation) {
            case WITHDRAWAL:
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

                System.out.println(multiLanguageInput.getEnterAmountMessage());

                BigDecimal amount = scanner.nextBigDecimal();

                // TODO: 2/9/20 check that atm has an amount and has denominations to construct requested sum (Zadacha o rukzake)

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
                bankAccountService.withdraw(transaction);
                break;
            case VIEW_BALANCE:
                BigDecimal balance = bankAccountService.getBalanceByCardInfo(pin, cardNumber, cardholderName, cvv);
                System.out.println(balance);
                break;
            default:
                break;
        }
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

        BigInteger cardNumber = scanner.nextBigInteger();
        // TODO: 2/8/20 add checker that card number has 16 symbols
        return cardNumber;
    }

    private String askCardholderName() {
        System.out.println(multiLanguageInput.getCardholderNameMessage());
        return scanner.next();
    }

    private Integer askCvv() {
        System.out.println(multiLanguageInput.getCvvMessage());

        Integer cvv = scanner.nextInt();
        // TODO: 2/8/20 add checker that card number has 3 or 4 symbols
        return cvv;
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
        return operation;
    }

    private Integer askPin() {
        System.out.println(multiLanguageInput.getEnterPinMessage());
        Integer pin = scanner.nextInt();
        // TODO: 2/8/20 check that pin has 4 digits
        return pin;
    }

}
