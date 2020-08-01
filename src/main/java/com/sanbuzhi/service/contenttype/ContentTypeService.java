package com.sanbuzhi.service.contenttype;

import com.sanbuzhi.pojo.ContentTypeDomain;
import com.sanbuzhi.pojo_short.SpecialDomain;

import java.util.List;
import java.util.Map;

public interface ContentTypeService {
    //得到所有类型
    List<ContentTypeDomain> getAllTypes();
    //类型-1，如果为0，则删除类型
    void subNumbers(Integer ctypeid);
    //根据ctypeid得到类型
    ContentTypeDomain getCTypeDomain(Integer ctypeid);

    //返回专题页需要的内容{文章实体列表+文章类型实体列表+文章类型关系实体列表}
    List<SpecialDomain> whatSpecialPageNeed();
}
