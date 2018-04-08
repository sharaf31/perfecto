package com.my.currency.mocks;

import com.my.currency.entities.ExchangeUnit;
import com.my.currency.models.Exchange;

import java.util.Date;
import java.util.List;

/**
 * Created by sharaf on 4/7/18.
 */
public interface MockConvertor {

    public List<Exchange> getUniqueRadates(Date begin, Date endDate);

    public ExchangeUnit getLatestRateForDate(Date date);
}
