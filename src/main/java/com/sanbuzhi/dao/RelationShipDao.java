package com.sanbuzhi.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RelationShipDao {
/*    *//**
     * 根据文章主键获取关联
     *//*
    List<RelationShipDomain> getRelationShipByCid(@Param("cid") Integer cid);

    *//**
     * 根据项目主键获取关联
     *//*
    List<RelationShipDomain> getRelationShipByMid(@Param("mid") Integer mid);

    *//**
     * 获取数量
     *//*
    Long getCountById(@Param("cid") Integer cid, @Param("mid") Integer mid);

    *//**
     * 添加关联
     *//*
    int addRelationShip(RelationShipDomain relationShip);

    *//**
     * 根据文章主键和项目主键删除关联
     *//*
    int deleteRelationShipById(@Param("cid") Integer cid, @Param("mid") Integer mid);

    *//**
     * 根据文章主键删除关联
     *//*
    int deleteRelationShipByCid(@Param("cid") Integer cid);

    *//**
     * 根据项目主键删除关联
     *//*
    int deleteRelationShipByMid(@Param("mid") Integer mid);

    *//**
     * 更新关联
     *//*
    int updateRelationShip(RelationShipDomain relationShip);*/

}
