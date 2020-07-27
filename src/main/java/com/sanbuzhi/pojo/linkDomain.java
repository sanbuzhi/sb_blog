/**
 * 链接实体
 */
package com.sanbuzhi.pojo;

import lombok.Data;

@Data
public class linkDomain {
    /*表主键*/
    private Integer lid;
    /*链接分类id*/
    private Integer typeid;
    /*链接名字*/
    private String name;
    /*链接url*/
    private String url;
    /*点击数*/
    private Integer clicks;
    /*喜欢数*/
    private Integer likes;
}
