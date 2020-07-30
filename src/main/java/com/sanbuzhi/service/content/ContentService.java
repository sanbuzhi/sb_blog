package com.sanbuzhi.service.content;

import com.github.pagehelper.PageInfo;
import com.sanbuzhi.pojo.ContentDomain;
import com.sanbuzhi.pojo_short.ArticleTypeTag;
import com.sanbuzhi.pojo_short.cond.ContentCond;

import java.util.List;

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
    PageInfo<ContentDomain> getRecentlyArticle(int pageNum, int pageSize);


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

}
