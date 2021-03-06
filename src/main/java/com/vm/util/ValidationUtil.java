package com.vm.util;

import com.vm.exception.InvalidInputException;

public class ValidationUtil
{
    private static final int MAX_VAL = 3;


    public static boolean validateTotalPrice(int totPrice){
        return MAX_VAL > totPrice; 
    }


    /**
     * Validates all the arguments for the BIDS.
     * 
     * @param fromAccount
     * @param toAccount
     * @param amount
     * @throws InvalidInputException 
     * @throws InvalidAmountException
     */
    public static boolean validateArgs(String ssp, String tid, String adSize) throws InvalidInputException
    {
        if (CommonUtil.isNotNullOrEmpty(ssp) && CommonUtil.isNotNullOrEmpty(tid) && CommonUtil.isNotNullOrEmpty(adSize))
        {
            try
            {
                Integer.parseInt(ssp);
            }
            catch (NumberFormatException nbe)
            {
                throw new InvalidInputException("Input must be a integer");
            }

            return true;

        }
        else
        {
            throw new InvalidInputException("Input are required and mandatory");
        }

    }


    
}
