package com.vm.model;

import lombok.Data;

@Data
public class BannerPojo
{
    private Banners[] banners;

    @Override
    public String toString()
    {
        return "ClassPojo [banners = "+banners+"]";
    }
}
