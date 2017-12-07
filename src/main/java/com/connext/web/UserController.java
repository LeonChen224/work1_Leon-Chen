package com.connext.web;

import com.connext.entity.User;
import com.connext.service.UserService;
import com.connext.utils.UserUtil;
import javafx.beans.binding.ObjectExpression;
import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private static Logger log = Logger.getLogger(UserController.class);

    //解锁时间
    private static final int UNLOCKTIME = 24*60*60*1000;

    @Autowired
    private UserService userService;

    @RequestMapping("/validUserphone")
    public String validUserphone(@RequestParam("userphone") String userphone){
        log.info("UserController is validUserphone start..."+userphone);

        boolean b = this.userService.validUserphone(userphone);
        log.info("该手机号是否被注册-->"+b);

        if(b){
            return "phoneExist";
        }
        return  "phoneNone";
    }

    @RequestMapping("/getNumberCode")
    public int getNumberCode(){
        log.info("UserController is getNumberCode start...");

        int numberCode = (int) ((Math.random()*9+1)*100000);
        log.info("numberCode-->"+numberCode);

        return numberCode;
    }

    @RequestMapping("/register")
    public String register(@RequestParam("userphone") String userphone, @RequestParam("userpwd") String userpwd){
        log.info("UserController is register start...");
        log.info("userphone-->"+userphone+",userpwd-->"+userpwd);

        String userpwdmd5 = UserUtil.md5(userpwd);
        log.info("register md5加密后-->"+userpwdmd5);

        boolean b = this.userService.register(userphone,userpwdmd5);
        if(b){
            log.info("注册新用户成功");
            return "registerSuccess";
        }
        return "registerFail";
    }

    @RequestMapping("/login")
    public String login(HttpSession session, @RequestParam("userphone") String userphone, @RequestParam("userpwd") String olduserpwd){
        log.info("UserController is login start..");
        log.info("userphone-->"+userphone+",olduserpwd-->"+olduserpwd);

        String userpwd = UserUtil.md5(olduserpwd);
        log.info("register md5加密后-->"+userpwd);

        List<User> userlist = this.userService.login(userphone,userpwd);
        String userpwddata = "";
        int lockflag = 0;
        int failnum = 0;
        Date failtime = null;
        Date locktime = null;
        int status = 0;
        for(User u:userlist){
            userpwddata = u.getUserpwd();
            lockflag = u.getLockflag();
            failnum = u.getFailnum();
            failtime = u.getFailtime();
            locktime = u.getLocktime();
            status = u.getStatus();
        }
        log.info("lockflag:"+lockflag+",failnum:"+failnum+",failtime:"+failtime+",locktime:"+locktime+",status:"+status);

        //1.判断用户是否锁定
        //1.1锁定直接返回
        if(lockflag == 1){
            //比较locktime和当前时间，是否超过24h。若超过，则解除锁定
            // failnum = 0,failtime = null,lockflag = 0,locktime = null;
            Date nowDate = new Date();
            // 24*60*60*1000
            if((nowDate.getTime()-locktime.getTime()) >= UNLOCKTIME){
                log.info("锁定状态已经超过24h,准备解除锁定");
                int newfailnum = 0;
                Date newfailtime = null;
                int newlockflag = 0;
                Date newlocktime = null;
                boolean b = this.userService.lockOrUnlockUser(userphone,newfailnum,newfailtime,newlockflag,newlocktime);
                if(b){
                    log.info("解除锁定成功");

                    List<User> newuserlist = this.userService.login(userphone,userpwd);
                    for(User u:newuserlist){
                        userpwddata = u.getUserpwd();
                        lockflag = u.getLockflag();
                        failnum = u.getFailnum();
                        failtime = u.getFailtime();
                        locktime = u.getLocktime();
                        status = u.getStatus();
                    }
                }else {
                    log.info("解除锁定失败");
                }
            }else {
                log.info("锁定状态还未到24h,返回锁定状态");
                return "phonelock";
            }
        }

        //1.2未锁定，进行 2.密码匹配
        //2.1密码匹配正确，update failnum=0 AND failtime = null
        if(userpwd.equals(userpwddata)){
            int newfailnum = 0;
            Date newfailtime = null;
            boolean b = this.userService.updateUser(userphone,newfailnum,newfailtime);
            if(b){
                log.info("密码匹配正确，将该账号状态还原更新成功");
                session.setAttribute("status",status);
                session.setAttribute("userphone",userphone);
                return "loginSuccess";
            }else{
                log.info("密码匹配正确，但该账号状态还原更新失败");
                return "loginFail";
            }
        }

        //2.2密码匹配不正确，update failnum AND failtime
        if(!userpwd.equals(userpwddata)){
            log.info("密码匹配不正确");

            //3.判断failnum次数：3.1 failnum<2 --> failnum += 1,failtime = 当前时间；
            if(failnum <= 2){
                failnum += 1;
                //Date failtime = sdf.format(new Date());
                Date newfailtime = new Date();
                log.info("+1后的failnum-->"+failnum);
                boolean b = this.userService.updateUser(userphone,failnum,newfailtime);
                if(b){
                    log.info("记录登录失败的次数和时间成功");

                }else{
                    log.info("记录登录失败的次数和时间失败");
                }
            }
            ///3.2 failnum=2 --> 获取failtime,与当前时间比较
            // (<=5min ,failnum=3,lockflag=1,记录locktime;>5min, failnum=0,failtime=null)
            log.info("line 149 failnum-->"+failnum);
            if(failnum == 3){
                Date nowDate = new Date();
                if((nowDate.getTime()-failtime.getTime()) > 5*60*1000) {
                    log.info("与第三次错误登录时间超过5分钟，准备清除状态");
                    int newfailnum = 0;
                    Date newfailtime = null;
                    boolean b = this.userService.updateUser(userphone,newfailnum,newfailtime);
                    if(b){
                        log.info("清除状态成功");
                    }else{
                        log.info("清除状态失败");
                    }
                }else {
                    log.info("与第三次错误登录时间在5分钟之内，第三次准备锁定");
                    int newfailnum = 3;
                    Date newfailtime = new Date();
                    int newlockflag = 1;
                    Date newlocktime = new Date();
                    boolean b = this.userService.lockOrUnlockUser(userphone,newfailnum,newfailtime,newlockflag,newlocktime);
                    if(b){
                        log.info("锁定用户成功");
                        return "phonelock";
                    }else {
                        log.info("锁定用户失败");
                    }
                }
            }
        }
        return "loginFail";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        log.info("UserController is logout start...");

        String userphone = (String) session.getAttribute("userphone");
        log.info("session userphone-->"+userphone);

        session.removeAttribute("userphone");
        log.info("UserController is logout end...");

        return "logoutSuccess";
    }
}
