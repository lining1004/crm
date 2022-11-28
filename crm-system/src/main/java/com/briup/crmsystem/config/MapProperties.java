package com.briup.crmsystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * @Author lining
 * @Date 2022/11/25
 */
@Component
@Data
@ConfigurationProperties("test")
public class MapProperties {
    private MultiValueMap<String,String> map;
}
