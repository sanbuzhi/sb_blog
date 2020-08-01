package com.sanbuzhi.service.contenttag;

import com.sanbuzhi.pojo.ContentTagDomain;

import java.util.List;

public interface ContentTagService {
    List<ContentTagDomain> getAllTags();

    ContentTagDomain getCTagDomainByCtagid(Integer ctagid);

    public void subNumbers(Integer ctagid);
}
