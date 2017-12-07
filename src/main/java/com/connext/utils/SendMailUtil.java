package com.connext.utils;

import com.connext.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMailUtil {
    Logger log = Logger.getLogger(CodeImageUtil.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserService userService;

    @RequestMapping("/mail/sendMail")
    public String sendMail(@RequestParam("userphone") String userphone){
        log.info("SendMailUtil is sendMail start...");
        log.info("userphone-->"+userphone);

        //生成4位随机数字
        int resetPwd = (int) ((Math.random()*9+1)*100000);
        log.info("resetPwd-->"+resetPwd);

        //构建javamail发送对象
        SimpleMailMessage message = new SimpleMailMessage();

        //发送者
        message.setFrom("1114312926@qq.com");

        //接受者
        message.setTo("1114312926@qq.com");

        //发送主题
        message.setSubject(userphone+"重置密码");

        //邮件内容
        String resetMsg = userphone+"账号重置密码为:"+resetPwd;
        message.setText(resetMsg);

        log.info("javaMailSender-->"+javaMailSender+",message-->"+message);
        this.javaMailSender.send(message);
        log.info("邮件发送成功");

        //对重置的密码md5加密
        String resetPwdStr = String.valueOf(resetPwd);
        log.info("resetPwdStr-->"+resetPwdStr);
        String userpwdmd5 = UserUtil.md5(resetPwdStr);
        log.info("userpwdmd5-->"+userpwdmd5);

        //更新数据库
        boolean b = this.userService.updateUserpwd(userphone,userpwdmd5);
        if(b){
            log.info("更新密码成功");
            return resetMsg;
        }
        return "更新失败";
    }



}
