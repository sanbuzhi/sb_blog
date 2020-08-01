package com.sanbuzhi.service.contenttagrel.impl;

import com.sanbuzhi.dao.ContentTagRelDao;
import com.sanbuzhi.service.contenttagrel.ContentTagRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentTagRelServiceImpl implements ContentTagRelService {
    @Autowired
    private ContentTagRelDao contentTagRelDao;

    @Override
    public List<Integer> getCTagidByCid(Integer cid) {
        List<Integer> ctagids = contentTagRelDao.getCtagid(cid);
        return ctagids;
    }

    @Override
    public void deleteContentTagRelByCtagid(Integer ctagid) {
        contentTagRelDao.deleteContentTagRelByCtagid(ctagid);
    }
}
