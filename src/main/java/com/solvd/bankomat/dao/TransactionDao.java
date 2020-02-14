package com.solvd.bankomat.dao;

import com.solvd.bankomat.db.TransactionMapper;
import com.solvd.bankomat.model.Transaction;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class TransactionDao {

    private static final Logger LOGGER = Logger.getLogger(TransactionDao.class);

    public void create(Transaction transaction) {
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            TransactionMapper mapper = session.getMapper(TransactionMapper.class);
            mapper.create(transaction);
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
