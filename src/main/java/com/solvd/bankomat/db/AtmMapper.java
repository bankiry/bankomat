package com.solvd.bankomat.db;

import com.solvd.bankomat.model.Atm;
import org.apache.ibatis.annotations.Param;

public interface AtmMapper {

    Atm getAtmById(Long id);

    void removeBankNoteById(@Param("banknoteId") Long banknoteId, @Param("atmId") Long atmId);
}
