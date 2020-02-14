package com.solvd.bankomat.dao;

import com.solvd.bankomat.db.AtmMapper;
import com.solvd.bankomat.model.Atm;
import com.solvd.bankomat.model.BankNote;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AtmDao {

    private static final Logger LOGGER = Logger.getLogger(AtmDao.class);

    public Atm getAtmById(Long id) {
        Atm result = null;
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            AtmMapper mapper = session.getMapper(AtmMapper.class);
            result = mapper.getAtmById(id);

            result.setLanguages(new ArrayList<>());
            result.getLanguages().add(Atm.Language.RUSSIAN);
            result.getLanguages().add(Atm.Language.ENGLISH);

            result.setOperations(new ArrayList<>());
            result.getOperations().add(Atm.Operation.WITHDRAWAL);
            result.getOperations().add(Atm.Operation.VIEW_BALANCE);
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

    public void removeBankNotes(List<BankNote> bankNotes, Long atmId) {
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            AtmMapper mapper = session.getMapper(AtmMapper.class);
            for (BankNote bankNote : bankNotes) {
                mapper.removeBankNoteById(bankNote.getId(), atmId);
            }
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
