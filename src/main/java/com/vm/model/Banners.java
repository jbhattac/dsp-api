package com.vm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Banners
{
    private int bidPrice;

    private String id;

    private int budget;

    private boolean active;

    private Size size;


    @Override
    public String toString()
    {
        return "ClassPojo [bidPrice = " + bidPrice + ", id = " + id + ", budget = " + budget + ", active = " + active + ", size = " + size + "]";
    }

}
