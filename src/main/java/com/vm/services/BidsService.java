package com.vm.services;

import com.vm.exception.InvalidInputException;
import com.vm.model.Bids;

public interface BidsService
{
    public Bids createBids(String ssp,String tid,String adSize)  throws InvalidInputException;
}
