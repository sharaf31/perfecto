package com.my.currency.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by sharaf on 4/6/18.
 */
@JsonAutoDetect
@JsonIgnoreProperties
public class Response {


    @JsonProperty("base_currency")
    private String base;
    @JsonProperty("exchange_values")
    private List<Exchange> exchange;

    public Response() {
    }

    ;

    public Response(String base, List<Exchange> exchange) {
        this.base = base;
        this.exchange = exchange;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public List<Exchange> getExchange() {
        return exchange;
    }

    public void setExchange(List<Exchange> exchange) {
        this.exchange = exchange;
    }
}
