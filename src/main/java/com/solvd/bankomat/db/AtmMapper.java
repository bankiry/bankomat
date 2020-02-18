package com.solvd.bankomat.db;

import com.solvd.bankomat.model.Atm;
import org.apache.ibatis.annotations.Param;

public interface AtmMapper {

    void create(Atm atm);

    Atm getAtmById(Long id);

    void removeById(Long id);

    void addBanknote(@Param("banknoteId") Long banknoteId, @Param("atmId") Long atmId);

    void removeBankNoteById(@Param("banknoteId") Long banknoteId, @Param("atmId") Long atmId);
}
