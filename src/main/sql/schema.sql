CREATE DATABASE IF NOT EXISTS connext DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS commonnews;
DROP TABLE IF EXISTS personnews;
DROP TABLE IF EXISTS newscontents;

-- users表,status:1-管理员可操作消息，0-普通用户，lockflag:1-锁定，0-未锁定，
CREATE TABLE users(
  id INT NOT NULL AUTO_INCREMENT ,
  userphone VARCHAR(11) NOT NULL ,
  userpwd VARCHAR(45) NOT NULL ,
  status INT NOT NULL DEFAULT 0,
  failtime DATETIME DEFAULT NULL ,
  failnum INT NOT NULL DEFAULT 0,
  lockflag INT NOT NULL DEFAULT 0,
  locktime DATETIME DEFAULT NULL ,
  PRIMARY KEY(id,userphone)
);

-- 普通消息表 commonnews
CREATE TABLE commonnews(
  id INT NOT NULL AUTO_INCREMENT ,
  cid VARCHAR(20) NOT NULL ,
  cname VARCHAR(45) NOT NULL ,
  ctime DATE NOT NULL,
  PRIMARY KEY (id,cid)
);

-- 个人新建消息表 personnews
CREATE TABLE personnews(
  id INT NOT NULL AUTO_INCREMENT ,
  pid VARCHAR(20) NOT NULL ,
  pname VARCHAR(45) NOT NULL ,
  ptime DATE NOT NULL,
  PRIMARY KEY (id,pid)
);

-- 所有消息内容表 newscontents
CREATE TABLE newscontents(
  id INT NOT NULL AUTO_INCREMENT ,
  cpid VARCHAR(20) NOT NULL ,
  content TEXT NOT NULL ,
  PRIMARY KEY (id,cpid)
);

-- 插入数据
INSERT INTO commonnews(`cid`,`cname`,`ctime`)
VALUES
  ('001','消息标题1','2017-9-26'),
  ('002','消息标题2','2017-10-1'),
  ('003','消息标题3','2017-10-28');

INSERT INTO newscontents(`cpid`,`content`)
VALUES
  ('001','消息正文1'),
  ('002','消息正文2'),
  ('003','消息正文3');