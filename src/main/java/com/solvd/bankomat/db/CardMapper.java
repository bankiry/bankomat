package com.solvd.bankomat.db;

import com.solvd.bankomat.model.Card;

import java.math.BigInteger;

public interface CardMapper {

    Card getBySecurityInfo(BigInteger cardNumber, String cardholderName, Integer cvv);
}
