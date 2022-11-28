package com.briup.crmsystem.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.briup.crmresource.exception.ServiceException;
import com.briup.crmresource.utils.ResultCode;
import com.briup.crmsystem.Constant.CRMSystemConstant;
import com.briup.crmsystem.config.SMSProperties;
import com.briup.crmsystem.config.GiteeAppProperties;
import com.briup.crmsystem.entity.GiteeUser;
import com.briup.crmsystem.entity.User;
import com.briup.crmsystem.entity.UserAuths;
import com.briup.crmsystem.mapper.UserAuthsMapper;
import com.briup.crmsystem.mapper.UserMapper;
import com.briup.crmsystem.service.IUserService;
import com.briup.crmuitl.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;
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
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAuthsMapper userAuthsMapper;
    @Autowired
    private GiteeAppProperties giteeAppProperties;
    @Autowired
    private SMSProperties SMSProperties;

    public String login(String username, String password) {
        //根据用户名查询用户信息
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        QueryWrapper<User> eq = wrapper.eq("usr_name", username);
        User user = userMapper.selectOne(eq);
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

    public String authByGitee(String code) {
        String token = null;
        try {
            MultiValueMap<String, String> authInfo = giteeAppProperties.getAuthInfo();
            authInfo.add("code", code);
            HttpEntity<Object> request = new HttpEntity<>(authInfo,null);
            String body = restTemplate.postForObject(giteeAppProperties.getOauth_uri(), request, String.class);
            Map<String,String> resultMap = objectMapper.readValue(body, Map.class);
            String accessToken = resultMap.get("access_token");
            //获取授权gitee用户的资料
            GiteeUser giteeUser = restTemplate.getForObject(giteeAppProperties.getUser_uri(), GiteeUser.class,accessToken);

            User user = null;
            QueryWrapper<UserAuths> wrapper = new QueryWrapper<>();
            QueryWrapper<UserAuths> eq = wrapper.eq("identity_type","gitee")
                                                .eq("identifier",giteeUser.getId());//gitee唯一标识
            UserAuths userAuths = userAuthsMapper.selectOne(eq);
            //如果用户第一次使用gitee登录系统
            if(userAuths == null){
                //1.自动生成站内账号
                user = User.builder()
                        .email("12345@qq.com")
                        .flag(CRMSystemConstant.ACCOUNT_STATUS_ENABLED)
                        .roleId(CRMSystemConstant.ACCOUNT_ROLE_MANAGER)
                        .name(giteeUser.getName()+ UUID.randomUUID())
                        .password(UUID.randomUUID().toString())
                        .build();
                userMapper.insert(user);
                //2.保存站外账号信息，并关联站内账号
                UserAuths userAuth = UserAuths.builder()
                        .userId(user.getId())
                        .identityType("gitee").identifier(giteeUser.getId()).credential(accessToken).build();
                userAuthsMapper.insert(userAuth);
            }
            //验证通过，生成token字符串
            token = JwtUtil.sign(user.getName(), null);
            //将token字符串保存到redis中 利用缓存机制保存，保存20分钟
            redisTemplate.opsForValue().set(token, user, 20, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return token;
    }

    public void sendSMS(String phoneNumber) {
        try {
            //创建配置信息
            Config config = new Config();
            config.setAccessKeyId(SMSProperties.getAccessKey_id());//api秘钥
            config.setAccessKeySecret(SMSProperties.getAccessKey_secret());//api秘钥
            config.setEndpoint(SMSProperties.getEndpoint());//访问域名
            //创建客户端
            Client client = new Client(config);
            //生成4位验证码[1000-9999]，并存储在redis中
            Integer code = (int) (Math.random() * 9000) + 1000;

            redisTemplate.opsForValue().set(phoneNumber, String.valueOf(code), 3, TimeUnit.MINUTES);
            //3.发送短信
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(phoneNumber)
                    .setSignName(SMSProperties.getSignName())
                    .setTemplateCode(SMSProperties.getTemplateCode())
                    .setTemplateParam(objectMapper.writeValueAsString(new SMSProperties.TemplateParam(code)));
            SendSmsResponse result = client.sendSms(sendSmsRequest);
            if(! "OK".equals(result.getBody().code)){
                //抛出错误信息
                throw new RuntimeException(result.body.message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String loginByPhoneNumber(String phoneNumber, String code) {
        //查询redis中保存的验证码
        Object verification  = redisTemplate.opsForValue().get(phoneNumber);
        if (code != null & !code.equals(verification)){
            //提示用户 验证码错误
            throw new ServiceException(ResultCode.USER_VERIFICATION_CODE_ERROR);
        }
        //验证通过，通过手机号获取用户信息
        QueryWrapper<UserAuths> wrapper = new QueryWrapper<>();
        QueryWrapper<UserAuths> eq = wrapper.eq("identity_type", CRMSystemConstant.IDENTITY_TYPE_PHONE)
                .eq("identifier", phoneNumber);
        Long userId = userAuthsMapper.selectOne(eq).getUserId();
        User user = userMapper.selectById(userId);
        //返回token字符串
        String token = JwtUtil.sign(user.getName(), null);
        //将用户信息保存在redis中
        redisTemplate.opsForValue().set(token, user, 20, TimeUnit.MINUTES);
        return token;
    }
}
