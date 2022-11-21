package com.briup.crmsystem.controller;

import com.briup.crmsystem.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lining
 * @Date 2022/11/13
 */
@Api(tags = "用户管理")
@RestController
public class SystemController {
    @Autowired
    private IUserService service;

    @GetMapping("/auth/user")
    public String findUserById(Integer id) {
        return "hello system";
    }
}
