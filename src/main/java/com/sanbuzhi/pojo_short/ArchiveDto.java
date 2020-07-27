package com.sanbuzhi.pojo_short;

import com.sanbuzhi.pojo.ContentDomain;
import lombok.Data;

import java.util.List;

/**
 * 文章归档类
 */
@Data
public class ArchiveDto {

    private String date;
    private String count;
    private List<ContentDomain> articles;
}
