package com.solvd.bankomat.dao;

import com.solvd.bankomat.db.BanknoteMapper;
import com.solvd.bankomat.model.BankNote;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class BanknoteDao {

    private static final Logger LOGGER = Logger.getLogger(BanknoteDao.class);

    public BankNote createBanknote(BankNote bankNote) {
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            BanknoteMapper mapper = session.getMapper(BanknoteMapper.class);
            mapper.create(bankNote);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (session != null) {
                session.commit();
                session.flushStatements();
                session.close();
            }
        }
        return bankNote;
    }

    public void removeById(Long id) {
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            BanknoteMapper mapper = session.getMapper(BanknoteMapper.class);
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
