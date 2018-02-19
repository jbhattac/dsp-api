package com.vm.services.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.config.BannerConfig;
import com.vm.config.BannerConfig.Token;

/**
 * We put things that are related to the Banner service 
 * things here.
 * <p/>
 */
@Service
public class BannerServiceImpl
{
    private final RestTemplate restTemplate;

    private final BannerConfig config;

    private static final Logger LOGGER = LoggerFactory.getLogger(BannerServiceImpl.class);


    public BannerServiceImpl(RestTemplate restTemplate, BannerConfig config)
    {
        super();
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public void getAllBanners(){
        
    }

    public void  createToken() throws JsonProcessingException
    {
         ResponseEntity<String> response =
            restTemplate.exchange(config.getAuthUrl(), HttpMethod.POST, 
                createJsonRequest(new ObjectMapper().writeValueAsString(config.getAuth())), String.class);
         ;
         try
        {
            config.setToken(new ObjectMapper().readValue(response.getBody(), Token.class));
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block need to write the proper code
            e.printStackTrace();
        }
         
    }


    private HttpEntity<String> createJsonRequest(String jsonInString)
        throws JsonProcessingException
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(jsonInString, headers);
        LOGGER.info("Deleting Entity :: " + jsonInString);
        return entity;
    }

}
