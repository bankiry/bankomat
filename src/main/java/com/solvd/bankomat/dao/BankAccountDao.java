package com.solvd.bankomat.dao;

import com.solvd.bankomat.db.BankAccountMapper;
import com.solvd.bankomat.model.BankAccount;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

public class BankAccountDao {

    private static final Logger LOGGER = Logger.getLogger(BankAccountDao.class);

    public BankAccount getByCardId(Long cardId) {
        BankAccount result = null;
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            BankAccountMapper bankAccountMapper = session.getMapper(BankAccountMapper.class);
            result = bankAccountMapper.getByCardId(cardId);
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

    public int updateAmountById(Long id, BigDecimal amount, Integer transactionCounter) {
        int result = 0;
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            BankAccountMapper bankAccountMapper = session.getMapper(BankAccountMapper.class);
            result = bankAccountMapper.updateAmountById(id, amount, transactionCounter);
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

}
