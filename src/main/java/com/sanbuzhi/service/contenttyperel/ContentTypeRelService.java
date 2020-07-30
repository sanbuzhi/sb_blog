package com.sanbuzhi.service.contenttyperel;

import com.sanbuzhi.pojo.ContentTypeRelDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentTypeRelService {
    /*增加关联*/
    void addContentTypeRel(ContentTypeRelDomain contentTypeRelDomain);
    /*删除关联*/
    void deleteContentTypeRel(ContentTypeRelDomain contentTypeRelDomain);

    /*根据类型ctypeid得到对应的文章cid列表*/
    List<Integer> getCid(@Param("ctypeid") Integer ctypeid);
    /*根据文章cid得到对应的类型ctypeid列表*/
    List<Integer> getCtypeid(@Param("cid") Integer cid);

    /*根据文章cid删除关联*/
    void deleteContentTypeRelByCid(@Param("cid") Integer cid);
    /*根据类型ctypeid删除关联*/
    void deleteContentTypeRelByCtypeid(@Param("ctypeid") Integer ctypeid);
}
