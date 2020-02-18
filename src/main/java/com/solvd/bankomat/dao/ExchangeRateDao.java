package com.solvd.bankomat.dao;

import com.solvd.bankomat.db.ExchangeRateMapper;
import com.solvd.bankomat.model.ExchangeRate;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class ExchangeRateDao {

    private static final Logger LOGGER = Logger.getLogger(ExchangeRateDao.class);

    public ExchangeRate createExchangeRate(ExchangeRate exchangeRate, Long bankId) {
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            ExchangeRateMapper mapper = session.getMapper(ExchangeRateMapper.class);
            mapper.create(exchangeRate, bankId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (session != null) {
                session.commit();
                session.flushStatements();
                session.close();
            }
        }
        return exchangeRate;
    }

    public void removeById(Long id) {
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            ExchangeRateMapper mapper = session.getMapper(ExchangeRateMapper.class);
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
