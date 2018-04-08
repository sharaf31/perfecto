package com.my.currency;

import com.my.currency.entities.ExchangeUnit;
import com.my.currency.mocks.MockConvertor;
import com.my.currency.mocks.MockUSDConvertor;
import com.my.currency.models.Response;
import com.my.currency.repositories.ExchangeRepo;
import com.my.currency.service.CurrencyConvertorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by sharaf on 4/7/18.
 */
public class ServiceTest {

    private CurrencyConvertorService convertorService;
    private MockConvertor mockConvertor;
    private ExchangeRepo exchangeRepoMock;
    private ExchangeUnit exchangeUnit;
    private ExchangeUnit exchangeUnit2;
    private List<ExchangeUnit> exchangeUnitList;
    private Date dateString;
    private String formattedDate;


    @Before
    public void setUp() {
        formattedDate="22.03.2018";
        try {
            dateString=new SimpleDateFormat("dd.MM.yyyy").parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        exchangeRepoMock = Mockito.mock(ExchangeRepo.class);
        mockConvertor = Mockito.mock(MockConvertor.class);
        convertorService = new CurrencyConvertorService(exchangeRepoMock,mockConvertor);
        exchangeUnit2= new ExchangeUnit();
        exchangeUnit2.setUpdatedTimestamp(dateString);
        exchangeUnit2.setRate(new BigDecimal(1.25));
        exchangeUnit2.setToCurrency("USD");
        exchangeUnit2.setFromCurrency("EURO");
        exchangeUnitList= new ArrayList<>();
        exchangeUnitList.add(exchangeUnit2);

        exchangeUnit= new ExchangeUnit();
        exchangeUnit.setUpdatedTimestamp(new Date());
        exchangeUnit.setRate(new BigDecimal(1.25));
        exchangeUnit.setToCurrency("USD");
        exchangeUnit.setFromCurrency("EURO");


    }

    @Test
    public void getConverstionForRangeTest() throws Exception {
       when(mockConvertor.getLatestRateForDate(eq(dateString))).thenReturn(exchangeUnit2);
       when(exchangeRepoMock.save(eq(exchangeUnit2))).thenReturn(exchangeUnit2);
       when(exchangeRepoMock.getRateForStartOfTheDate(eq(dateString),eq(dateString),eq("EURO"))).thenReturn(exchangeUnitList);
        Response response = convertorService.getConverstionForRange(formattedDate,formattedDate,"EURO");
        assertNotNull(response.getExchange());

    }


    @Test
    public void getCurrentConvertionTest() throws Exception {
        when(exchangeRepoMock.getLatestRate(eq("USD"))).thenReturn(exchangeUnit);
        Response response = convertorService.getCurrentConvertion("USD");
        assertEquals(new BigDecimal(1.25), response.getExchange().get(0).getExchangeRate());

    }
}
