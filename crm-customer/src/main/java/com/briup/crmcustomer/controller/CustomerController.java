package com.briup.crmcustomer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.crmcommon.utils.Result;
import com.briup.crmcustomer.entity.Customer;
import com.briup.crmcustomer.service.ICustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lining
 * @Date 2022/11/13
 */
@Api(tags = "客户管理")
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService service;

    @ApiOperation("分页查询客户信息")
    @GetMapping("/page")
    public Result findByPage(Integer pageNum, Integer pageSize) {
        Page<Customer> page = service.page(new Page<>(pageNum, pageSize));
        return Result.success(page.getRecords());
    }
}

