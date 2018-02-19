package com.vm.controller;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vm.exception.InvalidInputException;
import com.vm.model.Bids;
import com.vm.services.BidsService;

/**
 * All operations with a Bids will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("bidder")
public class BidsController
{
    private static final Logger log = LoggerFactory.getLogger(BidsController.class);

    private final BidsService bidsService;


    public BidsController(BidsService bidsService)
    {
        super();
        this.bidsService = bidsService;
    }


    /**
     * POST  bidder -> Create a new account.
     * @throws InvalidInputException 
     * @throws InvalidAmountException 
     * @throws EntityNotFoundException 
     * 
     * For the purpose of the test we only consider ssp , tid ,adSize.
     * 
     */

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Bids createBids(@RequestParam("ssp") String ssp, @RequestParam("tid") String tid, @RequestParam("adSize") String adSize) throws InvalidInputException
    {

        log.debug("Adv SSP : {}", ssp);
        log.debug("Adv tid : {}", tid);
        log.debug("Adv adSize : {}", adSize);

        return bidsService.createBids(ssp, tid, adSize);

    }
}
