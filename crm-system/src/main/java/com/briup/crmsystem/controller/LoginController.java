package com.briup.crmsystem.controller;

import com.briup.crmresource.utils.Result;
import com.briup.crmsystem.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

/**
 * @Author lining
 * @Date 2022/11/15
 */
@Api(tags = "认证管理")
@RestController
public class LoginController {
    @Autowired
    private IUserService service;

    @ApiOperation("用户登录")
    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public Result login(String username, String password) {
        String token = service.login(username, password);
        return Result.success(token);
    }
}
