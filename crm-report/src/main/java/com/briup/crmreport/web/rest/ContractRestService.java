package com.briup.crmreport.web.rest;


import org.springframework.cloud.openfeign.FeignClient;

/**
 * 合同信息远程调用
 * @Author lining
 * @Date 2022/11/21
 */
@FeignClient(name = "crm-marketing")
public interface ContractRestService {

}
