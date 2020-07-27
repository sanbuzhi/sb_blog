package com.sanbuzhi.pojo_short;

import lombok.Data;

/**
 * 后台统计对象
 */
@Data
public class StatisticsDto {
    /*文章数*/
    private Long articles;
    /*评论数*/
    private Long comments;
    /*链接数*/
    private Long links;
    /*总计访问数*/
    private Long visits;
}
