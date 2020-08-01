package com.sanbuzhi.service.contenttyperel.impl;

import com.sanbuzhi.dao.ContentTypeRelDao;
import com.sanbuzhi.pojo.ContentTypeRelDomain;
import com.sanbuzhi.service.contenttyperel.ContentTypeRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentTypeRelServiceImpl implements ContentTypeRelService {
    @Autowired
    private ContentTypeRelDao contentTypeRelDao;

    @Override
    public void addContentTypeRel(ContentTypeRelDomain contentTypeRelDomain){
    }

    @Override
    public void deleteContentTypeRel(ContentTypeRelDomain contentTypeRelDomain) {
    }

    @Override
    public List<Integer> getCid(Integer ctypeid) {
        List<Integer> cids = contentTypeRelDao.getCid(ctypeid);
        return cids;
    }

    @Override
    public List<Integer> getCtypeid(Integer cid) {
        List<Integer> ctypeids = contentTypeRelDao.getCtypeid(cid);
        return ctypeids;
    }

    @Override
    public void deleteContentTypeRelByCid(Integer cid) {
        contentTypeRelDao.deleteContentTypeRelByCid(cid);
    }

    @Override
    public void deleteContentTypeRelByCtypeid(Integer ctypeid){
        contentTypeRelDao.deleteContentTypeRelByCtypeid(ctypeid);
    }
}
