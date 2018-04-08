package com.my.currency.mocks;

import com.my.currency.entities.ExchangeUnit;
import com.my.currency.models.Exchange;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sharaf on 4/5/18.
 */
@Service
public class MockUSDConvertor implements MockConvertor {

    private static final String USD = "USD";

    private BigDecimal getMockedUSDforEuro() {
        BigDecimal max = new BigDecimal(2 + ".0");
        BigDecimal randFromDouble = new BigDecimal(Math.random());
        BigDecimal actualRandomDec = randFromDouble.divide(max, BigDecimal.ROUND_DOWN);
        actualRandomDec = actualRandomDec.add(new BigDecimal(1)).setScale(3, BigDecimal.ROUND_DOWN);
        return actualRandomDec;
    }

    public List<Exchange> getUniqueRadates(Date begin, Date endDate) {
        List<Exchange> list = new LinkedList<Exchange>();
        while (begin.compareTo(endDate) < 0) {
            begin = new Date(begin.getTime() + 86400000);
            list.add(new Exchange(getMockedUSDforEuro(), USD, new Date(begin.getTime()).toString()));
        }

        return list;
    }

    public ExchangeUnit getLatestRateForDate(Date date) {
        ExchangeUnit exchangeUnit = new ExchangeUnit();
        exchangeUnit.setFromCurrency("EURO");
        exchangeUnit.setToCurrency(USD);
        exchangeUnit.setRate(getMockedUSDforEuro());
        if (date != null)
            exchangeUnit.setUpdatedTimestamp(date);
        else
            exchangeUnit.setUpdatedTimestamp(new Date());
        return exchangeUnit;
    }

}
