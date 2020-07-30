package com.sanbuzhi.pojo_short;

import com.sanbuzhi.pojo.ContentDomain;
import lombok.Data;


@Data
public class ArticleTypeTag {
    ContentDomain contentDomain;
    String types;
    String tags;
}
