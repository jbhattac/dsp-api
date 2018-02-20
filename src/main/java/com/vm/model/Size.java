package com.vm.model;

import lombok.Data;

@Data
public class Size
{
    private String height;
    private String width;
    
    @Override
    public String toString()
    {
        return "ClassPojo [height = "+height+", width = "+width+"]";
    }

}
