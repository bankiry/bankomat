package com.solvd.bankomat.dao;

import com.solvd.bankomat.db.CardMapper;
import com.solvd.bankomat.model.Card;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class CardDao {

    private static final Logger LOGGER = Logger.getLogger(CardDao.class);

    public Card getBySecurityInfo(Long number, String cardHolderName, Integer cvv) {
        Card result = null;
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            CardMapper mapper = session.getMapper(CardMapper.class);
            result = mapper.getBySecurityInfo(number, cardHolderName, cvv);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (session != null) {
                session.commit();
                session.flushStatements();
                session.close();
            }
        }
        return result;
    }

    public void updateCard(Card card) {
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            CardMapper mapper = session.getMapper(CardMapper.class);
            mapper.updateCard(card);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (session != null) {
                session.commit();
                session.flushStatements();
                session.close();
            }
        }
    }
}
