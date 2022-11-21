package com.briup.crmreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients //开启远程调用
@SpringBootApplication(scanBasePackages = "com.briup")
public class CrmReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmReportApplication.class, args);
    }

}
