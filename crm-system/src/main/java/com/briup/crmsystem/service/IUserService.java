package com.briup.crmsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.briup.crmsystem.entity.User;

/**
 * @Author lining
 * @Date 2022/11/15
 */
public interface IUserService extends IService<User> {
    String login(String username, String password);
}
