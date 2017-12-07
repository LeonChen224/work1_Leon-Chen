package com.connext.service.impl;

import com.connext.dao.UserDao;
import com.connext.entity.User;
import com.connext.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static Logger log = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public boolean validUserphone(String userphone) {
        log.info("UserServiceImpl is validUserphone start...");
        int count = this.userDao.validUserphoneInfo(userphone);
        if(count == 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean register(String userphone, String userpwd) {
        log.info("UserServiceImpl is register start...");
        return this.userDao.registerInfo(userphone,userpwd);
    }

    @Override
    public boolean updateUserpwd(String userphone, String userpwdmd5) {
        log.info("UserServiceImpl is updateUserpwd start...");
        return this.userDao.updateUserpwdInfo(userphone,userpwdmd5);
    }

    @Override
    public List<User> login(String userphone, String userpwd) {
        log.info("UserServiceImpl is login start...");
        return this.userDao.loginInfo(userphone,userpwd);
    }

    @Override
    public boolean updateUser(String userphone,int failnum,Date failtime) {
        log.info("UserServiceImpl is updateUser start..."+failnum);
        return this.userDao.updateUserInfo(userphone,failnum,failtime);
    }

    @Override
    public boolean lockOrUnlockUser(String userphone, int newfailnum, Date newfailtime, int newlockflag, Date newlocktime) {
        log.info("UserServiceImpl is lockOrUnlockUser start..."+newfailnum);
        return this.userDao.lockOrUnlockUserInfo(userphone,newfailnum,newfailtime,newlockflag,newlocktime);
    }
}
