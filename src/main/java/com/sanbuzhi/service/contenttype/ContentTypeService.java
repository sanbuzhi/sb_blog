package com.sanbuzhi.service.contenttype;

import com.sanbuzhi.pojo.ContentTypeDomain;

import java.util.List;

public interface ContentTypeService {
    //得到所有类型
    List<ContentTypeDomain> getAllTypes();
    //类型-1，如果为0，则删除类型
    void subNumbers(Integer ctypeid);
}
