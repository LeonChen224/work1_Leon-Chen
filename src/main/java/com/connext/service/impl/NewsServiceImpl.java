package com.connext.service.impl;

import com.connext.dao.NewsDao;
import com.connext.entity.News;
import com.connext.service.NewsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private static Logger log = Logger.getLogger(NewsServiceImpl.class);

    @Autowired
    private NewsDao newsDao;

    @Override
    @Transactional
    public boolean insertOneNewsContent(String cid, String content) {
        log.info("NewsServiceImpl is insertOneNewsContent start...");
        return this.newsDao.insertOneNewsContentInfo(cid,content);
    }

    @Override
    @Transactional
    public boolean insertOneNews(String cid, String cname, Date ctime) {
        log.info("NewsServiceImpl is insertOneNews start...");
        return this.newsDao.insertOneNewsInfo(cid,cname,ctime);
    }

    @Override
    @Transactional
    public boolean deleteOneNewsContent(String cid) {
        log.info("NewsServiceImpl is deleteOneNewsContent start...");
        return this.newsDao.deleteOneNewsContentInfo(cid);
    }

    @Override
    @Transactional
    public boolean deleteOneNews(String cid) {
        log.info("NewsServiceImpl is deleteOneNews start...");
        return this.newsDao.deleteOneNewsInfo(cid);
    }

    @Override
    public boolean updateNewsContent(String cid, String editcontent) {
        log.info("NewsServiceImpl is updateNewsContent start...");
        return this.newsDao.updateNewsContentInfo(cid,editcontent);
    }

    @Override
    public boolean updateNews(String cid, String editcname, Date editctime) {
        log.info("NewsServiceImpl is updateNews start...");
        return this.newsDao.updateNewsInfo(cid,editcname,editctime);
    }

    @Override
    public String queryContentByCid(String cid) {
        log.info("NewsServiceImpl is queryContentByCid start...");
        return this.newsDao.queryContentByCidInfo(cid);
    }

    @Override
    public List<News> queryAllCommonNews() {
        log.info("NewsServiceImpl is queryAllCommonNews start...");
        return this.newsDao.queryAllCommonNewsInfo();
    }
}
