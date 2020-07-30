package com.sanbuzhi.dao;

import com.sanbuzhi.pojo.ContentTagDomain;
import com.sanbuzhi.pojo.ContentTypeDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ContentTagDao {
    //添加文章类型
    int addTag(ContentTagDomain contentTagDomain);
    //删除文章类型
    int deleteTag(@Param("ctagid") Integer ctagid);
    //更新文章类型
    int updateTag(ContentTagDomain contentTagDomain);
    //根据类型id查找类型
    ContentTagDomain searchTagById(@Param("ctagid") Integer ctagId);
    //根据类型名称查找类型
    ContentTagDomain searchTagByName(@Param("ctagname") String ctagname);
    //给某文章类型对应的文章数量=>number+1
    int addNumber(@Param("ctagid") Integer ctagid);
    //给某文章类型对应的文章数量=>number-1
    int subNumber(@Param("ctagid") Integer ctagid);
    //获取所有的标签
    List<ContentTagDomain> getAllTags();
}