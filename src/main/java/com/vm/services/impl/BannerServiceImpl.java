package com.vm.services.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.config.BannerConfig;
import com.vm.config.BannerConfig.Token;
import com.vm.model.BannerPojo;
import com.vm.model.Banners;

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

    private ConcurrentHashMap<String, Banners> store = new ConcurrentHashMap<>();

    private static final SimpleDateFormat dateFormat =
        new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


    public BannerServiceImpl(RestTemplate restTemplate, BannerConfig config)
    {
        super();
        this.restTemplate = restTemplate;
        this.config = config;
    }


    @Scheduled(fixedRate = 10000)
    public void getAllBanners() throws RestClientException, JsonProcessingException
    {
        LOGGER.info("Starting to get banner data from the api ");
        if (null == config.getToken())
        {
            createToken();
        }
        HttpHeaders requestHeaders = createHeaders(true);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(requestHeaders);
        ResponseEntity<BannerPojo> response = restTemplate.exchange(config.getBannersUrl(), HttpMethod.GET, httpEntity, BannerPojo.class);
        BannerPojo banners = response.getBody();
        Arrays.asList(banners.getBanners())
            .forEach(p -> store.put(p.getId(), p));
        store.values().forEach(p->LOGGER.info(p.toString()));
        LOGGER.info("Updated the banner store " + dateFormat.format(new Date()));

    }


    public void createToken() throws JsonProcessingException
    {
        ResponseEntity<String> response =
            restTemplate.exchange(config.getAuthUrl(), HttpMethod.POST,
                createJsonRequest(new ObjectMapper().writeValueAsString(config.getAuth()), false), String.class);;
        try
        {
            config.setToken(new ObjectMapper().readValue(response.getBody(), Token.class));
        }
        catch (IOException e)
        {
            /*
             * what needs to happen in case the external 
             * url is broken and not available.
             * For now I am just looging it to logs but
             * that is not good enough.
             * 
             */
            // TODO Auto-generated catch block need to write the proper code
            LOGGER.error("Error in communicating with banner server ", e);
        }

    }


    private HttpEntity<String> createJsonRequest(String jsonInString, boolean authHeader)
        throws JsonProcessingException
    {
        HttpEntity<String> entity = new HttpEntity<String>(jsonInString, createHeaders(authHeader));
        LOGGER.info("Json String :: " + jsonInString);
        return entity;
    }


    private HttpHeaders createHeaders(boolean authHeader)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        if (authHeader)
        {
            headers.set("Authorization", config.getToken().getToken());
        }
        return headers;
    }

}
