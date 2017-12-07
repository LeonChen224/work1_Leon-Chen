package com.connext.web;

import com.connext.entity.News;
import com.connext.service.NewsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    private static Logger log = Logger.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    @RequestMapping("/queryAllCommonNews")
    public List<News> queryAllCommonNews(){
        log.info("NewsController is queryAllCommonNews start...");

        List<News> newslists = this.newsService.queryAllCommonNews();
        log.info("newslists-->"+newslists);

        return newslists;
    }

    @RequestMapping("/queryContentByCid")
    public String queryContentByCid(@RequestParam("cid") String cid){
        log.info("NewsController is queryContentByCid start..."+cid);
        String content = this.newsService.queryContentByCid(cid);
        log.info("content-->"+content);

        return content;
    }

    @RequestMapping("/updateNews")
    public String updateNews(@RequestParam("cid") String cid, @RequestParam("editcname") String editcname,
                             @RequestParam("editctime") String editctimestr, @RequestParam("editcontent") String editcontent){
        log.info("NewsController is updateNews start...");
        log.info("editctimestr-->"+editctimestr);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date editctime = null;
        try {
            editctime = sdf.parse(editctimestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("editctime-->"+editctime);

        boolean b1 = this.newsService.updateNews(cid,editcname,editctime);
        boolean b2 = this.newsService.updateNewsContent(cid,editcontent);
        log.info("b1-->"+b1+",b2-->"+b2);

        if(b1 && b2){
            return "updateSuccess";
        }
        return "updateFail";
    }

    @RequestMapping("/deleteOneNews")
    public String deleteOneNews(@RequestParam("cid") String cid){
        log.info("NewsController is deleteOneNews start...");
        log.info("cid-->"+cid);

        boolean b1 = this.newsService.deleteOneNews(cid);
        boolean b2 = this.newsService.deleteOneNewsContent(cid);
        log.info("b1-->"+b1+",b2-->"+b2);

        if(b1 && b2){
            return "deleteOneSuccess";
        }
        return "deleteOneFail";
    }

    @RequestMapping("/insertOneNews")
    public String insertOneNews(@RequestParam("cid") String cid,@RequestParam("cname") String cname,
                                @RequestParam("ctime") String ctimestr, @RequestParam("content") String content){
        log.info("NewsCotroller is insertOneNews start...");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date ctime = null;
        try {
            ctime = sdf.parse(ctimestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("cid-->"+cid+",cname-->"+cname+",ctime-->"+ctime+",content-->"+content);

        boolean b1 = this.newsService.insertOneNews(cid,cname,ctime);
        boolean b2 = this.newsService.insertOneNewsContent(cid,content);
        log.info("b1-->"+b1+",b2-->"+b2);

        if(b1 && b2){
            return "insertSuccess";
        }
        return "insertFail";
    }
}
