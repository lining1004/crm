package com.briup.crmsystem.controller;

import com.briup.crmresource.utils.Result;
import com.briup.crmsystem.config.GiteeAppProperties;
import com.briup.crmsystem.service.IUserService;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.MediaType;
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
    private GiteeAppProperties giteeAppProperties;
    @Autowired
    private IUserService service;

    @ApiOperation("通过阿里云短信服务获取手机验证码")
    @PostMapping("/sms")
    @ResponseBody
    public Result sendSMS(String phoneNumber){
        service.sendSMS(phoneNumber);
        return Result.success();
    }

    @ApiOperation("通过手机号登录")
    @PostMapping(value = "/loginByPhoneNumber",consumes = MediaType.APPLICATION_FORM_URLENCODED)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phoneNumber",value = "手机号"),
            @ApiImplicitParam(name = "code",value = "短信验证码")
    })
    @ResponseBody
    public Result loginByPhoneNumber(String phoneNumber,String code){
        String token = service.loginByPhoneNumber(phoneNumber,code);
        return Result.success(token);
    }

    @ApiOperation(value = "点击图标,使用gitee账号登录系统",notes = "直接通过浏览器访问进行测试")
    @GetMapping("/loginByGitee")
    public String loginByGitee(){
        String client_id = giteeAppProperties.getClient_id();
        //回调地址
        String redirect_uri = giteeAppProperties.getRedirect_uri();
        String url = giteeAppProperties.getAuthorize_uri()+client_id+"&redirect_uri="+redirect_uri+"&response_type=code";
        return "redirect:"+url;
    }
    @GetMapping(value = "/auth/gitee")
    @ResponseBody
    public Result<String> authByGitee(@RequestParam("code") String code){
        String token = service.authByGitee(code);
        System.out.println(Result.success(token));
        return Result.success(token);
    }
}