package com.solvd.bankomat.db;

import com.solvd.bankomat.model.Card;
import org.apache.ibatis.annotations.Param;

public interface CardMapper {

    Card getBySecurityInfo(@Param("number") Long number, @Param("cardHolderName") String cardHolderName, @Param("cvv") Integer cvv);

    void updateCard(Card card);
}
