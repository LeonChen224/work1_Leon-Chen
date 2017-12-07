package com.connext.dao;

import com.connext.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserDao {
    @Select("SELECT COUNT(*) FROM users WHERE userphone = #{userphone}")
    int validUserphoneInfo(@Param("userphone") String userphone);

    @Insert("INSERT INTO users(userphone,userpwd) VALUES (#{userphone},#{userpwd})")
    @ResultType(boolean.class)
    boolean registerInfo(@Param("userphone") String userphone, @Param("userpwd") String userpwd);

    @Select("SELECT * FROM users WHERE userphone = #{userphone}")
    List<User> loginInfo(@Param("userphone") String userphone, @Param("userpwd") String userpwd);

    @Update("UPDATE users SET failnum = #{failnum}, failtime = #{failtime} WHERE userphone = #{userphone}")
    @ResultType(boolean.class)
    boolean updateUserInfo(@Param("userphone") String userphone, @Param("failnum") int failnum, @Param("failtime") Date failtime);

    @Update("UPDATE users SET failnum = #{newfailnum}, failtime = #{newfailtime}, lockflag = #{newlockflag}, locktime = #{newlocktime} WHERE userphone = #{userphone}")
    @ResultType(boolean.class)
    boolean lockOrUnlockUserInfo(@Param("userphone") String userphone, @Param("newfailnum")  int newfailnum,
            @Param("newfailtime") Date newfailtime, @Param("newlockflag") int newlockflag, @Param("newlocktime") Date newlocktime);

    @Update("UPDATE users SET userpwd = #{userpwdmd5}, failnum = 0,failtime = null,lockflag = 0,locktime = null WHERE userphone = #{userphone}")
    @ResultType(boolean.class)
    boolean updateUserpwdInfo(@Param("userphone") String userphone, @Param("userpwdmd5") String userpwdmd5);
}
