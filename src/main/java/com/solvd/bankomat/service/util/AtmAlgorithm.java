package com.solvd.bankomat.service.util;

import com.solvd.bankomat.exception.AtmException;
import com.solvd.bankomat.model.BankNote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AtmAlgorithm {

    public static List<BankNote> run(Integer amount, List<BankNote> denominations) throws AtmException {
        denominations.sort(Collections.reverseOrder());

        int constructedSum = 0;
        List<BankNote> denominationsToAdd = new ArrayList<>();
        for (BankNote denomination : denominations) {
            int currentTry = amount - constructedSum - denomination.getDenomination();
            if (currentTry >= 0) {
                constructedSum = constructedSum + denomination.getDenomination();

                denominationsToAdd.add(denomination);
            }
        }

        if (constructedSum != amount) {
            throw new AtmException("Atm has not required denominations");
        }
        return denominationsToAdd;
    }
}
