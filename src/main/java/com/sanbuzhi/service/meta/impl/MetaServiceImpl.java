/*
package com.sanbuzhi.service.meta.impl;

import com.sanbuzhi.constant.ErrorConstant;
import com.sanbuzhi.constant.Types;
import com.sanbuzhi.constant.WebConst;
import com.sanbuzhi.dao.MetaDao;
import com.sanbuzhi.dao.RelationShipDao;
import com.sanbuzhi.pojo.ContentDomain;
import com.sanbuzhi.service.content.ContentService;
import com.sanbuzhi.service.meta.MetaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * Created by Donghua.Chen on 2018/4/29.
 *//*

@Service
@Transactional
public class MetaServiceImpl implements MetaService {

    @Autowired
    private MetaDao metaDao;

    @Autowired
    private RelationShipDao relationShipDao;


    @Autowired
    private ContentService contentService;

    */
/**
     * 添加类别
     *//*

    @Override
    @CacheEvict(value={"metaCaches","metaCache"},allEntries=true,beforeInvocation=true)
    public int addMeta(MetaDomain meta) {
        return metaDao.addMeta(meta);
    }

    */
/**
     *  这段逻辑有点混乱
     *//*

    @Override
    @CacheEvict(value={"metaCaches","metaCache"},allEntries=true,beforeInvocation=true)
    public int saveMeta(String type, String name, Integer mid) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(name)){
            MetaCond metaCond = new MetaCond();
            metaCond.setName(name);
            List<MetaDomain> metas = metaDao.getMetasByCond(metaCond);
            if (null == metas || metas.size() == 0){
                MetaDomain metaDomain = new MetaDomain();
                metaDomain.setName(name);
                if (null != mid){
                    MetaDomain meta = metaDao.getMetaById(mid);
                    if (null != meta)
                        metaDomain.setMid(mid);

                    metaDao.updateMeta(metaDomain);
                    //更新原有的文章分类
                    if(meta !=null) {
                        contentService.updateCategory(meta.getName(), name);
                    }
                } else {
                    metaDomain.setType(type);
                    metaDao.addMeta(metaDomain);
                }
            } else {
                return 0;
            }
            return 1;
        }
        else return 0;
    }

    */
/**
     *
     *//*

    @Override
    @CacheEvict(value={"metaCaches","metaCache"},allEntries=true,beforeInvocation=true)
    public int addMetas(Integer cid, String names) {
        if (null == cid || StringUtils.isBlank(names))
            return 0;
        String[] nameArr = StringUtils.split(names, ",");
        for (String name : nameArr) {
            this.saveOrUpdate(cid, name);
        }
    }

    */
/**
     *  保存或者更新分类，传入文章id和
     *//*

    @Override
    @CacheEvict(value={"metaCaches","metaCache"},allEntries=true,beforeInvocation=true)
    public int saveOrUpdate(Integer cid, String name) {
        MetaCond metaCond = new MetaCond();
        metaCond.setName(name);
        List<MetaDomain> metas = this.getMetas(metaCond);

        int mid;
        MetaDomain metaDomain;
        if (metas.size() == 1){
            MetaDomain meta = metas.get(0);
            mid = meta.getMid();
        }else if (metas.size() > 1){
            return 0;
        } else {
            metaDomain = new MetaDomain();
            metaDomain.setSlug(name);
            metaDomain.setName(name);
            this.addMeta(metaDomain);
            mid = metaDomain.getMid();
        }
        if (mid != 0){
            Long count = relationShipDao.getCountById(cid, mid);
            if (count == 0){
                RelationShipDomain relationShip = new RelationShipDomain();
                relationShip.setCid(cid);
                relationShip.setMid(mid);
                relationShipDao.addRelationShip(relationShip);
            }

        }
        return 1;
    }

    @Override
    @CacheEvict(value={"metaCaches","metaCache"},allEntries=true,beforeInvocation=true)
    public int deleteMetaById(Integer mid) {
        if (null == mid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);

        MetaDomain meta = metaDao.getMetaById(mid);
        if (null != meta){
            String type = meta.getType();
            String name = meta.getName();
            metaDao.deleteMetaById(mid);
            //需要把相关的数据删除
            List<RelationShipDomain> relationShips = relationShipDao.getRelationShipByMid(mid);
            if (null != relationShips && relationShips.size() > 0){
                for (RelationShipDomain relationShip : relationShips) {
                    ContentDomain article = contentService.getArticleById(relationShip.getCid());
                    if (null != article){
                        ContentDomain temp = new ContentDomain();
                        temp.setCid(relationShip.getCid());
                        if (type.equals(Types.CATEGORY.getType())) {
                            temp.setCategories(reMeta(name, article.getCategories()));
                        }
                        if (type.equals(Types.TAG.getType())) {
                            temp.setTags(reMeta(name, article.getTags()));
                        }
                        //将删除的资源去除
                        contentService.updateArticleById(temp);
                    }
                }
                relationShipDao.deleteRelationShipByMid(mid);
            }
        }



    }

    @Override
    @CacheEvict(value={"metaCaches","metaCache"},allEntries=true,beforeInvocation=true)
    public int updateMeta(MetaDomain meta) {
        if (null == meta || null == meta.getMid())
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        metaDao.updateMeta(meta);

    }

    @Override
    @Cacheable(value = "metaCache", key = "'metaById_' + #p0")
    public MetaDomain getMetaById(Integer mid) {
        if (null == mid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return metaDao.getMetaById(mid);
    }

    @Override
    @Cacheable(value = "metaCaches", key = "'metas_' + #p0")
    public List<MetaDomain> getMetas(MetaCond metaCond) {
        return metaDao.getMetasByCond(metaCond);
    }


    @Override
    @Cacheable(value = "metaCaches", key = "'metaList_' + #p0")
    public List<MetaDto> getMetaList(String orderby, int limit) {
        if (StringUtils.isNotBlank(type)){
            if (StringUtils.isBlank(orderby)) {
                orderby = "count desc, a.mid desc";
            }
            if (limit < 1 || limit > WebConst.MAX_POSTS) {
                limit = 10;
            }
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("type", type);
            paraMap.put("order", orderby);
            paraMap.put("limit", limit);
            return metaDao.selectFromSql(paraMap);
        }
        return null;
    }

    private String reMeta(String name, String metas) {
        String[] ms = StringUtils.split(metas, ",");
        StringBuilder sbuf = new StringBuilder();
        for (String m : ms) {
            if (!name.equals(m)) {
                sbuf.append(",").append(m);
            }
        }
        if (sbuf.length() > 0) {
            return sbuf.substring(1);
        }
        return "";
    }
}
*/
