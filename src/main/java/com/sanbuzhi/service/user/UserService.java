package com.sanbuzhi.service.user;

import com.sanbuzhi.pojo.UserDomain;
import org.springframework.stereotype.Service;

/**
 * Created by Donghua.Chen on 2018/4/20.
 */
@Service
public interface UserService {
    /**
     * 更改用戶信息
     */
    int updateUserInfo(UserDomain user);

    /**
     * 根据用户id获取用户信息
     */
    UserDomain getUserInfoById(Integer uId);


    /**
     * 用户登录
     */
    UserDomain login(String username, String password);

}
