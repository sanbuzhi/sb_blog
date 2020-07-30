package com.sanbuzhi.service.comment.impl;

import com.github.pagehelper.PageInfo;
import com.sanbuzhi.dao.CommentDao;
import com.sanbuzhi.pojo.CommentDomain;
import com.sanbuzhi.pojo_short.cond.CommentCond;
import com.sanbuzhi.service.comment.CommentService;
import com.sanbuzhi.service.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ContentService contentService;

    @Override
    public void addComment(CommentDomain commentDomain) {
        commentDao.addComment(commentDomain);
        //评论增加后，需要对文章的评论数+1
        Integer cid = commentDomain.getCid();
        contentService.updateArticleCommentCountById(cid);
    }

    @Override
    public void deleteComment(Integer coid) {

    }

    @Override
    public void updateCommentStatus(Integer coid, String status) {

    }

    @Override
    public CommentDomain getCommentById(Integer coid) {
        return null;
    }

    @Override
    public List<CommentDomain> getCommentsByCId(Integer cid) {
        List<CommentDomain> comments = commentDao.getCommentsByCId(cid);
        return comments;
    }

    @Override
    public PageInfo<CommentDomain> getCommentsByCond(CommentCond commentCond, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public List<CommentDomain> getAllComments() {
        List<CommentDomain> allComments = commentDao.getAllComment();
        return allComments;
    }

    @Override
    public List<CommentDomain> getAllCommentsPassed() {
        List<CommentDomain> allComments = this.getAllComments();
        //状态为通过则添加，返回
        return allComments;
    }
}
