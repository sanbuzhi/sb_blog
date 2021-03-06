package com.sanbuzhi.pojo;


import lombok.Data;

/**
 * 评论相关
 */

@Data
public class CommentDomain {
    /*评论主键*/
    private Integer coid;
    /*评论内容*/
    private String content;

    /*关联主键,文章编号*/
    private Integer cid;


    /*评论的时间戳*/
    private String created;

    /*评论者作者名字*/
    private String authorName;
    /*评论者邮件*/
    private String email;

    /*评论状态*/
    private String status;
    /*父级评论*/
    private Integer parentcoid;

    private static final long serialVersionUID = 1L;

}
