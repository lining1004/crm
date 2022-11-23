package com.briup.crmmarketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.briup")
public class CrmMarketingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmMarketingApplication.class, args);
    }

}
