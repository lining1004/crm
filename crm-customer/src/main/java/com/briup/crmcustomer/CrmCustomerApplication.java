package com.briup.crmcustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.briup")  //扫描crm-customer,crm-common模块中的注解
public class CrmCustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrmCustomerApplication.class, args);
    }
}
