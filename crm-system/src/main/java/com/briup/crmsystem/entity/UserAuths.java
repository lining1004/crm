package com.briup.crmsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户授权信息表
 * </p>
 *
 * @author briup
 * @since 2022-11-28
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_user_auths")
public class UserAuths implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 用户id
     */
      private Long userId;

      /**
     * 登录类型（手机号 邮箱 用户名 第三方）
     */
      private String identityType;

      /**
     * 标识  手机号 邮箱 用户名 或第三方应用的唯一标识
     */
      private String identifier;

      /**
     * 密码凭证 站内的保存密码 站外的不保存或者保存token
     */
      private String credential;
    
    public Long getId() {
        return id;
    }

      public void setId(Long id) {
          this.id = id;
      }
    
    public Long getUserId() {
        return userId;
    }

      public void setUserId(Long userId) {
          this.userId = userId;
      }
    
    public String getIdentityType() {
        return identityType;
    }

      public void setIdentityType(String identityType) {
          this.identityType = identityType;
      }
    
    public String getIdentifier() {
        return identifier;
    }

      public void setIdentifier(String identifier) {
          this.identifier = identifier;
      }
    
    public String getCredential() {
        return credential;
    }

      public void setCredential(String credential) {
          this.credential = credential;
      }

    @Override
    public String toString() {
        return "UserAuths{" +
              "id = " + id +
                  ", userId = " + userId +
                  ", identityType = " + identityType +
                  ", identifier = " + identifier +
                  ", credential = " + credential +
              "}";
    }
}
