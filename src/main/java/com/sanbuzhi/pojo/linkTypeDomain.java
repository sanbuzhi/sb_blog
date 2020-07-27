/**
 * 链接类型实体
 */

package com.sanbuzhi.pojo;

import lombok.Data;

@Data
public class linkTypeDomain {
    /*链接类型主键*/
    private Integer typeid;
    /*链接类型名称*/
    private String name;
    /*链接类型描述*/
    private String description;
}
