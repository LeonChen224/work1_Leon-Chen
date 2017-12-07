package com.connext.service;

import com.connext.entity.User;

import java.util.Date;
import java.util.List;

public interface UserService {
    boolean validUserphone(String userphone);

    List<User> login(String userphone, String userpwd);

    boolean register(String userphone, String userpwd);

    //更新该账户状态
    boolean updateUser(String userphone, int failnum, Date failtime);

    //锁定该账户
    boolean lockOrUnlockUser(String userphone, int newfailnum, Date newfailtime, int newlockflag, Date newlocktime);

    //更新密码
    boolean updateUserpwd(String userphone, String userpwdmd5);
}
