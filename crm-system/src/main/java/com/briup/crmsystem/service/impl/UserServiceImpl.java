package com.briup.crmsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.briup.crmresource.exception.ServiceException;
import com.briup.crmresource.utils.ResultCode;
import com.briup.crmsystem.Constant.CRMSystemConstant;
import com.briup.crmsystem.entity.User;
import com.briup.crmsystem.mapper.UserMapper;
import com.briup.crmsystem.service.IUserService;
import com.briup.crmuitl.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author lining
 * @Date 2022/11/15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserMapper mapper;

    @Override
    public String login(String username, String password) {
        //根据用户名查询用户信息
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        QueryWrapper<User> eq = wrapper.eq("usr_name", username);
        User user = mapper.selectOne(eq);
        //判断用户名
        if(user == null){
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        //判断用户密码
        if(!user.getPassword().equals(password)){
            throw new ServiceException(ResultCode.USER_PASSWD_ERROR);
        }
        //判断用户账号
        if (user.getFlag().equals(CRMSystemConstant.ACCOUNT_STATUS_DISABLED)){
            throw new ServiceException(ResultCode.USER_ACCOUNT_FORBIDDEN);
        }
        //验证通过，生成token字符串
        String token = JwtUtil.sign(username, null);
        //将token字符串保存到redis中 利用缓存机制保存，保存20分钟
        redisTemplate.opsForValue().set(token, user, 20, TimeUnit.MINUTES);
        //返回token字符串
        return token;
    }
}
