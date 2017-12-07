package com.connext.service;

import com.connext.entity.News;

import java.util.Date;
import java.util.List;

public interface NewsService {
    //查找所有公共消息
    List<News> queryAllCommonNews();

    //查找消息正文
    String queryContentByCid(String cid);

    //更新消息
    boolean updateNews(String cid, String editcname, Date editctime);

    //更新消息内容
    boolean updateNewsContent(String cid, String editcontent);

    //删除一条消息
    boolean deleteOneNews(String cid);
    boolean deleteOneNewsContent(String cid);

    //新增一条消息
    boolean insertOneNews(String cid, String cname, Date ctime);
    boolean insertOneNewsContent(String cid, String content);
}
