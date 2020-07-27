/*
package com.sanbuzhi.service.meta;

import java.util.List;

*/
/**
 * 类别服务层
 * Created by Donghua.Chen on 2018/4/29.
 *//*

public interface MetaService {
    */
/**
     * 添加项目
     * @param meta
     * @return
     *//*

    int addMeta(MetaDomain meta);

    */
/**
     * 添加
     * @param type
     * @param name
     * @param mid
     *//*

    int saveMeta(String type, String name, Integer mid);



    */
/**
     * 批量添加
     * @param cid
     * @param names
     *//*

    int addMetas(Integer cid, String names);



    */
/**
     * 添加或者更新
     * @param cid
     * @param name
     *//*

    int saveOrUpdate(Integer cid, String name);

    */
/**
     * 删除项目
     * @param mid
     * @return
     *//*

    int deleteMetaById(Integer mid);

    */
/**
     * 更新项目
     * @param meta
     * @return
     *//*

    int updateMeta(MetaDomain meta);

    */
/**
     * 根据编号获取项目
     * @param mid
     * @return
     *//*

    MetaDomain getMetaById(Integer mid);

    */
/**
     * 获取所有的项目
     * @param metaCond 查询条件
     * @return
     *//*

    List<MetaDomain> getMetas(MetaCond metaCond);

    */
/**
     * 根据类型查询项目列表，带项目下面的文章数
     * @param orderby
     * @param limit
     * @return
     *//*

    List<MetaDto> getMetaList(String orderby, int limit);
}
*/
