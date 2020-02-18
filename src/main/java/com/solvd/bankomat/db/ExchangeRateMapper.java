package com.solvd.bankomat.db;

import com.solvd.bankomat.model.ExchangeRate;
import org.apache.ibatis.annotations.Param;

public interface ExchangeRateMapper {

    void create(@Param("exchangeRate") ExchangeRate exchangeRate, @Param("bankId") Long bankId);

    void removeById(Long id);
}
