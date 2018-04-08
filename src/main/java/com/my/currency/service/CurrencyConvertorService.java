package com.my.currency.service;

import com.my.currency.Exception.ForexException;
import com.my.currency.entities.ExchangeUnit;
import com.my.currency.mocks.MockConvertor;
import com.my.currency.models.Exchange;
import com.my.currency.models.Response;
import com.my.currency.repositories.ExchangeRepo;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by sharaf on 4/6/18.
 */
@Service
public class CurrencyConvertorService {


    private static final String DE_DATE_FORMATE = "dd.MM.yyyy";

    private MockConvertor convertor;

    private ExchangeRepo exchangeRepo;

    public CurrencyConvertorService(ExchangeRepo exchangeRepo, MockConvertor convertor) {
        this.exchangeRepo = exchangeRepo;
        this.convertor = convertor;
    }


    public Response getCurrentConvertion(String base) {

        ExchangeUnit unit = exchangeRepo.getLatestRate(base);
        Exchange exchange = new Exchange(unit.getRate(), unit.getToCurrency(), unit.getUpdatedTimestamp().toString());
        List<Exchange> exchangeList = Stream.of(exchange).collect(Collectors.toList());
        Response response = new Response(unit.getFromCurrency(), exchangeList);

        return response;
    }

    public Response getConverstionForRange(String fromDate, String toDate, String base) throws ForexException {

        Date begin = validateDateFormate(fromDate);
        Date endDate = validateDateFormate(toDate);
        // Validate the date range
        validateDateRange(begin, endDate);
        List<Exchange> exchangeList = new LinkedList<Exchange>();

        while (begin.compareTo(endDate) <= 0) {
            // Upper boundy's end
            Date rangeEnd = new Date(begin.getTime() + 86399000);
            List<ExchangeUnit> exchangeUnitList = exchangeRepo.getRateForStartOfTheDate(begin, rangeEnd, base);
            ExchangeUnit exchangeUnit = null;
            if (exchangeUnitList == null || exchangeUnitList.size() == 0) {
                exchangeUnit = exchangeRepo.save(convertor.getLatestRateForDate(begin));
                exchangeList.add(new Exchange(exchangeUnit.getRate(), exchangeUnit.getToCurrency(), exchangeUnit.getUpdatedTimestamp().toString()));

            } else {
                for (ExchangeUnit resultsExchangeUnit : exchangeUnitList)
                    exchangeList.add(new Exchange(resultsExchangeUnit.getRate(), resultsExchangeUnit.getToCurrency(), resultsExchangeUnit.getUpdatedTimestamp().toString()));
            }

            begin = new Date(begin.getTime() + 86400000);
        }

        return new Response(base, exchangeList);
    }


    private Date validateDateFormate(String dateString) throws ForexException {
        Date date = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DE_DATE_FORMATE);
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            throw new ForexException("Date format Exception, please enter in " + DE_DATE_FORMATE + " format", e);

        }
        return date;
    }

    private void validateDateRange(Date beginDate, Date endDate) throws ForexException {
        Date currentDate = new Date();
        if (beginDate.after(currentDate) || endDate.after(currentDate)) {
            throw new ForexException(" Only historical dates are avaiable.Not future dates");
        }
        if (endDate.before(beginDate))
            throw new ForexException(" Invalid date range");

    }

    public ExchangeRepo getExchangeRepo() {
        return exchangeRepo;
    }

    public MockConvertor getConvertor() {
        return convertor;
    }

}
