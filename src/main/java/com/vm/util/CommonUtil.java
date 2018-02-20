package com.vm.util;

import com.vm.model.Size;

public class CommonUtil
{
    
    private static final String ADSIZESEP = "x";

    public static Size transferStringToSize(String adSize)
    {
        if (isNotNullOrEmpty(adSize))
        {
            String[] sizeArr = adSize.split("x");
            return new Size(sizeArr[0], sizeArr[1]);

        }
        return null;
    }


    public static boolean isNotNullOrEmpty(String str)
    {
        return str != null && !str.isEmpty() && !str.trim().equals("") && !str.trim().equalsIgnoreCase("null");
    }

}
