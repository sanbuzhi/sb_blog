package com.sanbuzhi.pojo_short;

import lombok.Data;

import java.util.List;

@Data
public class FileDomain {
    private String yearMonth;
    List<ArticleTypeTag> articleTypeTagList;
}
