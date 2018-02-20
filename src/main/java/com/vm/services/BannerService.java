package com.vm.services;

import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vm.model.Banners;
import com.vm.model.Size;

public interface BannerService
{

    void getAllBanners() throws RestClientException, JsonProcessingException;


    void createToken() throws JsonProcessingException;


    Banners getFilteredBanners(Size size);
}
