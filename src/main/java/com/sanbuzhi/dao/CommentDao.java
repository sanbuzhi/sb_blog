package com.sanbuzhi.dao;

import com.sanbuzhi.pojo.CommentDomain;
import com.sanbuzhi.pojo_short.cond.CommentCond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CommentDao {
    /**
     * 根据主键查找评论
     */
    CommentDomain getCommentByCoid(@Param("coid") Integer coid);

    /**
     * 根据文章主键查找评论
     */
    CommentDomain getCommentByCid(@Param("cid") Integer cid);

    /**
     * 根据关联主键-文章编号查找评论
     */
    List<CommentDomain> getCommentsByCId(@Param("cid") Integer cid);

    /**
     * 根据其他条件查找评论
     */
    List<CommentDomain> getCommentsByCond(CommentCond commentCond);

    /**
     * 获取文章数量
     */
    Long getCommentsCount();

    /**
     * 新增评论
     */
    int addComment(CommentDomain commentDomain);

    /**
     * 删除评论
     */
    int deleteComment(@Param("coid") Integer coid);

    /**
     * 更新评论的状态
     */
    int updateCommentStatus(@Param("coid") Integer coid, @Param("status") String status);

}
