package com.briup.crmsystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * gitee 第三方应用配置信息
 * @Author lining
 * @Date 2022/11/25
 */
@Data
@Component
@ConfigurationProperties(prefix = "gitee.auth")
public class GiteeAppProperties {
    private String authorize_uri;
    private String oauth_uri;
    private String redirect_uri;
    private String user_uri;
    private String email_uri;
    private String grant_type;
    private String client_id;
    private String client_secret;
    private String response_type;

    public MultiValueMap<String, String> getAuthInfo() {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("authorize_uri",authorize_uri);
        map.add("client_secret",client_secret);
        map.add("client_id",client_id);
        map.add("grant_type",grant_type);
        map.add("redirect_uri",redirect_uri);
        return map;
    }
}
