package com.my.currency.repositories;

import com.my.currency.entities.ExchangeUnit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by sharaf on 4/6/18.
 */
public interface ExchangeRepo extends CrudRepository<ExchangeUnit, Long> {


    @Query("select t from ExchangeUnit t where t.updatedTimestamp = ( select max(e.updatedTimestamp ) from ExchangeUnit e) AND t.fromCurrency = ?1")
    public ExchangeUnit getLatestRate(String base);


    @Query("select t from ExchangeUnit t where t.updatedTimestamp between ?1 and  ?2 and t.fromCurrency=?3")
    public List<ExchangeUnit> getRateForStartOfTheDate(Date updatedDate, Date end, String currency);

}
