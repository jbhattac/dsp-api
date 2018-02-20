package com.vm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.vm.config.BannerConfig;
import com.vm.config.SwaggerConfig;
import com.vm.services.impl.BannerServiceImpl;

@SpringBootApplication
@EnableScheduling
@Import(SwaggerConfig.class)
public class DspApp implements CommandLineRunner
{
    @Autowired BannerServiceImpl bannerService;
    @Autowired BannerConfig config;
    
    public static void main(String[] args)
    {
        SpringApplication.run(DspApp.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception
    {


    }
    
    
}
