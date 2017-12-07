package com.connext.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserUtil {
    // 使用MD5加密密码
    public static String md5(String message) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("md5");
            byte m5[] = md.digest(message.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(m5);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
