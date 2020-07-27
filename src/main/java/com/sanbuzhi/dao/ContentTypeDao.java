package com.sanbuzhi.dao;

import com.sanbuzhi.pojo.ContentTypeDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ContentTypeDao {
    //添加文章类型
    int addType(ContentTypeDomain contentTypeDomain);
    //删除文章类型
    int deleteType(@Param("ctypeId") Integer ctypeId);
    //更新文章类型
    int updateType(ContentTypeDomain contentTypeDomain);
    //根据类型id查找类型
    ContentTypeDomain searchTypeById(@Param("ctypeId") Integer ctypeId);
    //根据类型名称查找类型
    ContentTypeDomain searchTypeByName(@Param("ctypeName") String ctypeName);
    //给某文章类型对应的文章数量=>number+1
    int addNumber(@Param("ctypeId") Integer ctypeId);
    //给某文章类型对应的文章数量=>number-1
    int subNumber(@Param("ctypeId") Integer ctypeId);
}
