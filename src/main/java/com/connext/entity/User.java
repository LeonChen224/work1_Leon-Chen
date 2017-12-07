package com.connext.entity;

import java.util.Date;

public class User {
    private int id;
    private String userphone;
    private String userpwd;
    private int status;
    private Date failtime;
    private int failnum;
    private int lockflag;
    private Date locktime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getFailtime() {
        return failtime;
    }

    public void setFailtime(Date failtime) {
        this.failtime = failtime;
    }

    public int getFailnum() {
        return failnum;
    }

    public void setFailnum(int failnum) {
        this.failnum = failnum;
    }

    public int getLockflag() {
        return lockflag;
    }

    public void setLockflag(int lockflag) {
        this.lockflag = lockflag;
    }

    public Date getLocktime() {
        return locktime;
    }

    public void setLocktime(Date locktime) {
        this.locktime = locktime;
    }
}
