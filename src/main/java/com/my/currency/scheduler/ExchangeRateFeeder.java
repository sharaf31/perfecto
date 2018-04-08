package com.my.currency.scheduler;

import com.my.currency.service.CurrencyConvertorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by sharaf on 4/8/18.
 */
@Component
public class ExchangeRateFeeder {

    @Autowired
    CurrencyConvertorService convertorService;


    @Scheduled(cron = "${cron.string}")
    @PostConstruct
    public void feedEuroTOUsdData() {
        convertorService.getExchangeRepo().save(convertorService.getConvertor().getLatestRateForDate(null));
    }


}
