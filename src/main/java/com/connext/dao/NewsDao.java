package com.connext.dao;

import com.connext.entity.News;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NewsDao {

    @Select("SELECT * FROM commonnews")
    List<News> queryAllCommonNewsInfo();

    @Select("SELECT content FROM newscontents WHERE cpid = #{cid}")
    String queryContentByCidInfo(@Param("cid") String cid);

    @Update("UPDATE commonnews set cname = #{editcname},ctime = #{editctime} WHERE cid = #{cid}")
    @ResultType(boolean.class)
    boolean updateNewsInfo(@Param("cid") String cid, @Param("editcname") String editcname, @Param("editctime") Date editctime);

    @Update("UPDATE newscontents set content = #{editcontent} WHERE cpid = #{cid}")
    @ResultType(boolean.class)
    boolean updateNewsContentInfo(@Param("cid") String cid, @Param("editcontent") String editcontent);

    @Delete("DELETE from commonnews where cid = #{cid}")
    @ResultType(boolean.class)
    boolean deleteOneNewsInfo(@Param("cid") String cid);

    @Delete("DELETE from newscontents where cpid = #{cid}")
    @ResultType(boolean.class)
    boolean deleteOneNewsContentInfo(@Param("cid") String cid);

    @Insert("INSERT INTO commonnews(cid,cname,ctime) VALUES (#{cid},#{cname},#{ctime})")
    @ResultType(boolean.class)
    boolean insertOneNewsInfo(@Param("cid") String cid, @Param("cname") String cname, @Param("ctime") Date ctime);

    @Insert("INSERT INTO newscontents(cpid,content) VALUES (#{cid},#{content})")
    @ResultType(boolean.class)
    boolean insertOneNewsContentInfo(@Param("cid") String cid, @Param("content") String content);
}
