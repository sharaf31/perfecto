package com.my.currency.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by sharaf on 4/6/18.
 */
@JsonAutoDetect
public class Exchange {

    @JsonProperty("rate")
    private BigDecimal exchangeRate;

    @JsonProperty("exchange_currency")
    private String currency;

    @JsonProperty("updated_time")
    private String time;

    public Exchange() {
    }

    ;

    public Exchange(BigDecimal exchangeRate, String currency, String time) {
        this.exchangeRate = exchangeRate;
        this.currency = currency;
        this.time = time;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
