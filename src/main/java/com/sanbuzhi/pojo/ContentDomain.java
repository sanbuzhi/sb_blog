package com.sanbuzhi.pojo;

import lombok.Data;

/**
 * 内容相关
 */

@Data
public class ContentDomain {
    /*文章主键*/
    private Integer cid;
    /*标题*/
    private String title;
    /*标题图片*/
    private String titlePic;
    /*内容缩略名*/
    private String slug;
    /*生成时间*/
    private String created;
    /*更改时间*/
    private String modified;

    /*内容*/
    private String content;
    /*文章作者ID*/
    private String authorName;
    /*状态*/
    private String status;

    /*点击次数*/
    private Integer clicks;
    /*评论数*/
    private Integer commentsNum;
    /*是否允许评论*/
    private boolean allowComment;
}
