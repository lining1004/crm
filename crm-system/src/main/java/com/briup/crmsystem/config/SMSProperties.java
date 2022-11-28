package com.briup.crmsystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author lining
 * @Date 2022/11/28
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun")
public class SMSProperties {
    private String accessKey_id;
    private String accessKey_secret;
    private String endpoint;
    private String signName;
    private String templateCode;

    /**
     * 短信模块：默认使用了SMS_154950909code模版作为测试使用
     */
    public static class TemplateParam{
        public Integer code;

        public TemplateParam(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }
}

