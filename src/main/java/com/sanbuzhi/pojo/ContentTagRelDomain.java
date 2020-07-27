/**
 * 文章和标签的关系实体
 */
package com.sanbuzhi.pojo;

import lombok.Data;

@Data
public class ContentTagRelDomain {
    private Integer cid;
    private Integer ctagid;
}
