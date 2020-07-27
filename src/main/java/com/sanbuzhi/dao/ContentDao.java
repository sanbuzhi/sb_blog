package com.sanbuzhi.dao;

import com.sanbuzhi.pojo.ContentDomain;
import com.sanbuzhi.pojo_short.ArchiveDto;
import com.sanbuzhi.pojo_short.cond.ContentCond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ContentDao {
    /**
     * 根据文章主键查找文章
     */
    ContentDomain getArticleById(@Param("cid") Integer cid);

    /**
     * 根据条件获取文章{条件：状态，标题，内容，开始时间戳，结束时间戳
     */
    List<ContentDomain> getArticlesByCond(ContentCond contentCond);

    /**
     * 根据条件获取文章{条件：类别，标签=>跨表
     */
    //待补充
    //List<ContentDomain> getArticlesByCond1(ContentCond contentCond);

    /**
     * 获取归档数据
     * 查询条件：只包含开始和结束时间
     */
    List<ArchiveDto> getArchive(ContentCond contentCond);

    /**
     * 获取文章总数
     */
    Long getArticleCount();

    /**
     * 获取最近的文章（只包含id和title）
     */
    List<ContentDomain> getRecentlyArticle();

    /**
     * 搜索文章-根据标题 或 内容
     */
    List<ContentDomain> searchArticle(@Param("param") String param);

    /**
     * 增文章
     */
    int addArticle(ContentDomain contentDomain);

    /**
     * 删文章
     */
    int deleteArticleById(@Param("cid") Integer cid);

    /**
     * 改文章
     */
    int updateArticleById(ContentDomain contentDomain);

    /**
     * 更新文章的评论数
     * 假设网站没有删除评论的操作，则只需要不断+1即可。
     */
    int updateArticleCommentCountById(@Param("cid") Integer cid);

    /**
     * 更新文章的点击数
     * 不断+1即可
     */
    int updateArticleHitCountById(@Param("cid") Integer cid);

}
