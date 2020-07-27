/**
 * 文章和类型的关系实体
 */
package com.sanbuzhi.pojo;

import lombok.Data;

@Data
public class ContentTypeRelDomain {
    private Integer cid;
    private Integer ctypeid;
}
