package com.briup.crmsystem.service.impl;

import com.briup.crmsystem.entity.UserAuths;
import com.briup.crmsystem.mapper.UserAuthsMapper;
import com.briup.crmsystem.service.IUserAuthsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户授权信息表 服务实现类
 * </p>
 *
 * @author briup
 * @since 2022-11-28
 */
@Service
public class UserAuthsServiceImpl extends ServiceImpl<UserAuthsMapper, UserAuths> implements IUserAuthsService {

}
