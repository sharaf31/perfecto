package com.my.currency;

import com.my.currency.models.Response;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sharaf on 4/7/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class HttpRequestTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getLatestUSDRateTest() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/forex/get-latest",
                Response.class)!=null);
    }

    @Test
    public void getExchangeRateForDateTest() throws Exception {
    Response response=this.restTemplate.getForObject("http://localhost:" + port + "/forex/get-exchange-range?fromDate=22.10.2017&toDate=22.12.2017",
                Response.class);
        assertThat(response.getExchange().size()<0);
    }
}
