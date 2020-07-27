package com.sanbuzhi.service.comment;

import com.github.pagehelper.PageInfo;
import com.sanbuzhi.pojo.CommentDomain;
import com.sanbuzhi.pojo_short.cond.CommentCond;

import java.util.List;

/**
 * 评论服务层
 */
public interface CommentService {

    /**
     * 新增评论
     */
    int addComment(CommentDomain commentDomain);

    /**
     * 删除评论
     */
    int deleteComment(Integer coid);

    /**
     * 更新评论的状态
     */
    int updateCommentStatus(Integer coid, String status);


    /**
     * 查找单条评论
     */
    CommentDomain getCommentById(Integer coid);

    /**
     * 根据文章编号获取评论列表--只显示通过审核的评论-正常状态的
     * @param cid 文章主键编号
     * @return
     */
    List<CommentDomain> getCommentsByCId(Integer cid);

    /**
     * 根据条件获取评论列表
     * @param commentCond 查询条件
     * @param pageNum 分页参数 第几页
     * @param pageSize 分页参数 每页条数
     * @return
     */
    PageInfo<CommentDomain> getCommentsByCond(CommentCond commentCond, int pageNum, int pageSize);
}
