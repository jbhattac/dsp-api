package com.vm.services.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.config.BannerConfig;
import com.vm.config.BannerConfig.Token;
import com.vm.model.BannerPojo;
import com.vm.model.Banners;
import com.vm.model.Size;
import com.vm.services.BannerService;

/**
 * We put things that are related to the Banner service 
 * things here.
 * <p/>
 */
@Service
public class BannerServiceImpl implements BannerService
{
    private final RestTemplate restTemplate;

    private final BannerConfig config;

    private static final Logger LOGGER = LoggerFactory.getLogger(BannerServiceImpl.class);

    private static ConcurrentHashMap<String, Banners> store = new ConcurrentHashMap<>();

    private static final SimpleDateFormat dateFormat =
        new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


    public BannerServiceImpl(RestTemplate restTemplate, BannerConfig config)
    {
        super();
        this.restTemplate = restTemplate;
        this.config = config;
    }


    @Override
    public Banners getFilteredBanners(Size size)
    {
        final Comparator<Banners> bannerComp = (p1, p2) -> Integer.compare(p1.getBidPrice(), p2.getBidPrice());
        Banners banner = null;
        List<Banners> bannerList = store.values()
            .stream()
            .filter(p -> p.isActive())
            .filter(p -> size.equals(p.getSize()))
            .filter(p -> p.getBudget() > 0)
            .collect(Collectors.toList());

        if (Optional.ofNullable(bannerList).isPresent() && bannerList.size() > 0)
        {
            banner = bannerList.stream().max(bannerComp).get();
        }
        if (null != banner)
        {
            banner.setBudget(banner.getBudget() - banner.getBidPrice());
        }

        return banner;

    }


    /* (non-Javadoc)
     * @see com.vm.services.impl.BannerService#getAllBanners()
     */
    @Override
    @Scheduled(fixedRate = 10000)
    public void getAllBanners()
    {
        LOGGER.info("Starting to get banner data from the api ");

        try
        {
            if (null == config.getToken())
            {
                createToken();
            }
            HttpHeaders requestHeaders = createHeaders(true);
            HttpEntity<?> httpEntity = new HttpEntity<Object>(requestHeaders);
            ResponseEntity<BannerPojo> response = restTemplate.exchange(config.getBannersUrl(), HttpMethod.GET, httpEntity, BannerPojo.class);
            BannerPojo banners = response.getBody();
            Arrays.asList(banners.getBanners())
                .stream()
                .filter(p-> (null == store.get(p.getId())))
                .forEach(p -> store.put(p.getId(), p));
            store.values().forEach(p -> LOGGER.info(p.toString()));
            LOGGER.info("Updated the banner store " + dateFormat.format(new Date()));

        }
        catch (JsonProcessingException e)
        {
            LOGGER.error("Error in communicating with banner server store will not be updated ", e);

        }

    }


    /* (non-Javadoc)
     * @see com.vm.services.impl.BannerService#createToken()
     */
    @Override
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
