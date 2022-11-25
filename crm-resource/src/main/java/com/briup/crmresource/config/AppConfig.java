package com.briup.crmresource.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 项目业务模块统一的配置信息
 * @Author lining
 * @Date 2022/11/17
 */
@Configuration
@EnableDiscoveryClient  //开启服务发现
@MapperScan("com.briup.**.mapper")  //mybatis-plus扫描映射接口位置
@EnableSwagger2Doc     //开启swagger文档
public class AppConfig {
    @Bean
    public MybatisPlusInterceptor interceptor() {
        System.out.println("配置分页拦截器");
        //1.创建拦截器对象
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //2.添加分页拦截器 mysql limit语句
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
