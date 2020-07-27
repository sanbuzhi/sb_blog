package com.sanbuzhi.dao;

import com.sanbuzhi.pojo.ContentTypeRelDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ContentTypeRelDao {
    /*增加关联*/
    int addContentTypeRel(ContentTypeRelDomain contentTypeRelDomain);
    /*删除关联*/
    int deleteContentTypeRel(ContentTypeRelDomain contentTypeRelDomain);

    /*根据类型ctypeid得到对应的文章cid列表*/
    List<Integer> getCid(@Param("ctypeid") Integer ctypeid);
    /*根据文章cid得到对应的类型ctypeid列表*/
    List<Integer> getCtypeid(@Param("cid") Integer cid);

    /*根据文章cid删除关联*/
    int deleteContentTypeRelByCid(@Param("cid") Integer cid);
    /*根据类型ctypeid删除关联*/
    int deleteContentTypeRelByCtypeid(@Param("ctypeid") Integer ctypeid);
}
