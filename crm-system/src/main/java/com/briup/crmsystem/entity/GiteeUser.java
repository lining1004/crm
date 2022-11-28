package com.briup.crmsystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 第三方用户：gitee
 * @Author lining
 * @Date 2022/11/25
 */
@Data
public class GiteeUser implements Serializable {
    public static final long serialVersionUID = 1L;
    private String id;//唯一标识
    private String login;//登录名
    private String name;//用户名
    private String avatar_url;//用户头像
}