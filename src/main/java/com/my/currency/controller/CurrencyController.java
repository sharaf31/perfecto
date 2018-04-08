package com.my.currency.controller;

import com.my.currency.Exception.ForexException;
import com.my.currency.entities.ExchangeUnit;
import com.my.currency.models.Response;
import com.my.currency.service.CurrencyConvertorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by sharaf on 4/5/18.
 */
@RestController
@RequestMapping(value = "/forex", produces = MediaType.APPLICATION_JSON_VALUE)
public class CurrencyController {

    private Logger log = LoggerFactory.getLogger(CurrencyController.class);


    @Autowired
    CurrencyConvertorService convertor;

    @RequestMapping(value = "/get-latest", method = RequestMethod.GET)
    public ResponseEntity getLatestUSDrate(@RequestParam("base") Optional<String> base) throws ForexException {
        Response response = null;
        if (base.isPresent() && !base.get().equalsIgnoreCase("EURO"))
            return new ResponseEntity("Currently Euro only Supported", HttpStatus.BAD_REQUEST);
        else
            response = convertor.getCurrentConvertion("EURO");
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    // FIXME : utility method to see all the transaction

    @RequestMapping(value = "/get-all", method = RequestMethod.GET)
    public List<ExchangeUnit> getALL() {
        List<ExchangeUnit> units = new ArrayList<ExchangeUnit>();
        convertor.getExchangeRepo().findAll().forEach(unit -> units.add(unit));
        return units;
    }
/**/
    @RequestMapping(value = "/get-exchange-range", method = RequestMethod.GET)
    public ResponseEntity getExchangeRateforDate(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, @RequestParam("base") Optional<String> base) throws ForexException {
        Response response = null;
        if (base.isPresent() && !base.get().equalsIgnoreCase("EURO"))
            return new ResponseEntity("Currently Euro only Supported", HttpStatus.BAD_REQUEST);
        else
            try {

                return new ResponseEntity<Response>(convertor.getConverstionForRange(fromDate, toDate, "EURO"), HttpStatus.OK);
            } catch (ForexException e) {
                return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);

            }
    }
}


