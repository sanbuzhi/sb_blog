package com.sanbuzhi.pojo_short;

import com.sanbuzhi.pojo.ContentDomain;
import lombok.Data;

/**
 * 类型标签和文章实体的混合实体
 */
@Data
public class ArticleTTDomain {
    private String Type;
    private String Tag;
    private ContentDomain contentDomain;
}
