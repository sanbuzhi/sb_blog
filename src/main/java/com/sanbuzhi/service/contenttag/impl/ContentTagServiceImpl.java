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
}
