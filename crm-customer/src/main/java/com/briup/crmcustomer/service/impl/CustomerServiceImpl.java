package com.briup.crmcustomer.service.impl;

import com.briup.crmcustomer.entity.Customer;
import com.briup.crmcustomer.mapper.CustomerMapper;
import com.briup.crmcustomer.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author briup
 * @since 2022-11-15
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
