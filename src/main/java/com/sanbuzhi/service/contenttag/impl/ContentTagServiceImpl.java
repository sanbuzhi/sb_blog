package com.sanbuzhi.service.contenttag.impl;

import com.sanbuzhi.dao.ContentTagDao;
import com.sanbuzhi.pojo.ContentTagDomain;
import com.sanbuzhi.service.contenttag.ContentTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentTagServiceImpl implements ContentTagService {
    @Autowired
    private ContentTagDao contentTagDao;

    @Override
    public List<ContentTagDomain> getAllTags() {
        List<ContentTagDomain> allTags = contentTagDao.getAllTags();
        return allTags;
    }

    @Override
    public ContentTagDomain getCTagDomainByCtagid(Integer ctagid) {
        ContentTagDomain contentTagDomain = contentTagDao.searchTagById(ctagid);
        return contentTagDomain;
    }

    @Override
    public void subNumbers(Integer ctagid) {
        ContentTagDomain tagDomain = contentTagDao.searchTagById(ctagid);
        System.out.println("typeDomain.getNumbers()"+tagDomain.getNumbers());
        if(tagDomain.getNumbers() > 1){
            //减一
            contentTagDao.subNumber(ctagid);
        }else{
            //如果这个类型的数量只剩一个，则直接删除类型
            contentTagDao.deleteTag(ctagid);
        }
    }
}
