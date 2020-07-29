package com.sanbuzhi.dao;

import com.sanbuzhi.pojo.ContentTagRelDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ContentTagRelDao {
    /*增加关联*/
    int addContentTagRel(ContentTagRelDomain contentTagRelDomain);
    /*删除关联*/
    int deleteContentTagRel(ContentTagRelDomain contentTagRelDomain);

    /*根据标签ctagid得到对应的文章cid列表*/
    List<Integer> getCid(@Param("ctagid") Integer ctagid);
    /*根据文章cid得到对应的标签ctagid列表*/
    List<Integer> getCtagid(@Param("cid") Integer cid);
    /*根据文章cid删除关联*/
    int deleteContentTagRelByCid(@Param("cid") Integer cid);
    /*根据标签ctagid删除关联*/
    int deleteContentTagRelByCtagid(@Param("ctagid") Integer ctagid);
}
