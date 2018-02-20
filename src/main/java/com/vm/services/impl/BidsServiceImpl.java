package com.vm.services.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vm.config.BannerConfig;
import com.vm.exception.InvalidInputException;
import com.vm.model.Banners;
import com.vm.model.Bids;
import com.vm.model.Size;
import com.vm.services.BannerService;
import com.vm.services.BidsService;
import com.vm.util.CommonUtil;
import com.vm.util.ValidationUtil;

/**
 * Service to encapsulate the link between DAO and controller 
 * and to have business logic for some Bids specific things.
 * <p/>
 */
@Service
public class BidsServiceImpl implements BidsService
{
    private static final Logger log = LoggerFactory.getLogger(BidsServiceImpl.class);

    private AtomicInteger TOTALPRICE = new AtomicInteger(0);

    private final BannerService bannerService;
    
    private final BannerConfig config;

    public BidsServiceImpl(BannerService bannerService,BannerConfig config)
    {
        super();
        this.bannerService = bannerService;
        this.config = config;
    }


    /**
     * @throws InvalidInputException 
     * 
     */
    @Override
    public Bids createBids(String ssp, String tid, String adSize) throws InvalidInputException
    {
        ValidationUtil.validateArgs(ssp, tid, adSize);
        Size size = CommonUtil.transferStringToSize(adSize);
        Banners banner = bannerService.getFilteredBanners(size);
        if (null != banner)
        {
            return new Bids(banner.getBidPrice(), tid, config.getShowBannersUrl()+"/"+banner.getId());
        }
       /* if ("728x90".equalsIgnoreCase(adSize) && ValidationUtil.validateTotalPrice(TOTALPRICE.get()))
        {
            TOTALPRICE.incrementAndGet();
            return new Bids(1, tid, "http://example.org");
        }
        else if ("160x600".equalsIgnoreCase(adSize) && ValidationUtil.validateTotalPrice(TOTALPRICE.get()))
        {
            TOTALPRICE.incrementAndGet();
            return new Bids(1, tid, "http://wikipedia.org");
        }*/
        else
        {
            return new Bids(0, tid);
        }

    }

}
