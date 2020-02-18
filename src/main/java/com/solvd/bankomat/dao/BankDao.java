package com.solvd.bankomat.dao;

import com.solvd.bankomat.db.BankMapper;
import com.solvd.bankomat.model.Bank;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class BankDao {

    private static final Logger LOGGER = Logger.getLogger(BankDao.class);

    public Bank createBank(Bank bank) {
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            BankMapper mapper = session.getMapper(BankMapper.class);
            mapper.create(bank);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (session != null) {
                session.commit();
                session.flushStatements();
                session.close();
            }
        }
        return bank;
    }

    public void removeById(Long id) {
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            BankMapper mapper = session.getMapper(BankMapper.class);
            mapper.removeById(id);
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
