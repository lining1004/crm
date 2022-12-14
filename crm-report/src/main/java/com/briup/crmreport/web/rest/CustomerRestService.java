package com.briup.crmreport.web.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.crmreport.web.vm.CustomerVM;
import com.briup.crmresource.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 客户信息远程调用
 * @Author lining
 * @Date 2022/11/21
 */
@FeignClient(name = "crm-customer",path = "/customer")
public interface CustomerRestService {
    @GetMapping("/page")
    Result<List<CustomerVM>> findByPage(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
}
