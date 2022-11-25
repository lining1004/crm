package com.briup.crmsystem.controller;

import com.briup.crmresource.utils.Result;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 *
 * @Author lining
 * @Date 2022/11/24
 */
@Api(tags = "第三方登录认证")
@Controller
public class ThirdLoginController {
    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation("点击图标,使用gitee账号登录系统")
    @GetMapping("/loginByGitee")
    public String loginByGitee(){
        String client_id = "f076b00c996c2ccaa469c22c58a6a4b8486a6782c13d56e72cb9f7ee184d8253";
        //回调地址
        String redirect_uri = "http://localhost:9005/auth/gitee";
        String url = "https://gitee.com/oauth/authorize?client_id="+client_id+"&redirect_uri="+redirect_uri+"&response_type=code";
        return "redirect:"+url;
    }

    @GetMapping("/auth/gitee")
    @ResponseBody
    public Result authByGitee(@RequestParam("code") String code){
        String client_id = "f076b00c996c2ccaa469c22c58a6a4b8486a6782c13d56e72cb9f7ee184d8253";
        String redirect_uri = "http://localhost:9005/auth/gitee";
        String client_secret = "4eaeeedcdd28be67441eb52d36c97c1aadea9a3ba5c640c24ce52983fa99c494";
        String url = "https://gitee.com/oauth/token";
        MultiValueMap params = new LinkedMultiValueMap();
        params.add("grant_type","authorization_code");
        params.add("code", code);
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("client_secret", client_secret);
        HttpEntity<Object> request = new HttpEntity<>(params,null);
        String body = restTemplate.postForObject(url, request, String.class);
        System.out.println(body);
        return Result.success(body);
    }
}
