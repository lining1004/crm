package com.briup.crmsystem.Constant;

/**
 * @Author lining
 * @Date 2022/11/18
 */
public interface CRMSystemConstant {
    /**
     * 账号启用
     */
    Integer ACCOUNT_STATUS_ENABLED = 1;
    /**
     * 账号禁用
     */
    Integer ACCOUNT_STATUS_DISABLED = 0;
    /**
     * 客户经理角色
     */
    Integer ACCOUNT_ROLE_MANAGER = 4;
    /*
        登录类型:手机号
     */
    String IDENTITY_TYPE_PHONE = "phone";
    /*
        登录类型:gitee授权
     */
    String IDENTITY_TYPE_GITEE = "gitee";
}
