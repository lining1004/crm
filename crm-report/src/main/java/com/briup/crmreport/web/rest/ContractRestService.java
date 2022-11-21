package com.briup.crmreport.web.rest;

import org.springframework.cloud.openfeign.FeignClient;

/**
 *
 * @Author lining
 * @Date 2022/11/21
 */
@FeignClient(name = "crm-marketing")
public interface ContractRestService {

}
