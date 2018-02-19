package com.vm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.vm.config.SwaggerConfig;

@SpringBootApplication
@Import(SwaggerConfig.class)
public class DspApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(DspApp.class, args);
    }
}
