package com.sanbuzhi.service.content;

import com.github.pagehelper.PageInfo;
import com.sanbuzhi.pojo.ContentDomain;
import com.sanbuzhi.pojo_short.ArticleTypeTag;
import com.sanbuzhi.pojo_short.FileDomain;
import com.sanbuzhi.pojo_short.cond.ContentCond;

import java.util.List;
import java.util.Map;

/**
 * 文章业务层
 * Created by Donghua.Chen on 2018/4/29.
 */
public interface ContentService {

    /**
     * 添加文章
     * @param contentDomain
     * @return
     */
    void addArticle(ContentDomain contentDomain,String type,String tag);

    /**
     * 根据编号删除文章
     * @param cid
     * @return
     */
    void deleteArticleById(Integer cid);

    /**
     * 文章阅读量+1
     * @param cid
     */
    void updateArticleHitCountById(Integer cid);

    /**
     * 文章评论量+1
     */
    void updateArticleCommentCountById(Integer cid);

    /**
     * 根据编号获取文章
     * @param cid
     * @return
     */
    ContentDomain getArticleById(Integer cid);

    /**
     * 根据条件获取文章列表
     * @param contentCond
     * @return
     */
    PageInfo<ContentDomain> getArticlesByCond(ContentCond contentCond, int pageNum, int pageSize);

    /**
     * 获取最近的文章（只包含id和title）
     * @param pageNum
     * @param pageSize
     * @return
     */
    Map getRecentlyArticle(Integer pageNum, Integer pageSize);


    /**
     * 获取所有文章
     */
    List<ContentDomain> getAllArticle();

    /**
     * 获取所有文章+类型+标签
     */
    List<ArticleTypeTag> getAllArticleTypeTag();

    /**
     * 搜索文章
     * @param param
     * @param pageNun
     * @param pageSize
     * @return
     */
    PageInfo<ContentDomain> searchArticle(String param, int pageNun, int pageSize);

    /**
     * 修改文章
     * 返回给修改页面内容{文章实体，对应所有types，和tags}
     * 同时删除此文章对应的type和tag还有关联等信息
     */
    Map ContentUpdateEdit(Integer cid);

    /**
     * 更新文章
     */
    void updateArticle(ContentDomain contentDomain,String type,String tag);

    /**
     * 获取文章数量
     */
    Long getArticleCount();

    /**
     * 返回专题页需要的内容{文章实体列表+文章类型实体列表+文章类型关系实体列表}
     */
    List<FileDomain> whatFilePageNeed();

}
