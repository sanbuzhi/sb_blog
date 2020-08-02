package com.sanbuzhi.service.contenttype.impl;

import com.sanbuzhi.dao.ContentTypeDao;
import com.sanbuzhi.pojo.ContentDomain;
import com.sanbuzhi.pojo.ContentTypeDomain;
import com.sanbuzhi.pojo_short.SpecialDomain;
import com.sanbuzhi.service.content.ContentService;
import com.sanbuzhi.service.contenttype.ContentTypeService;
import com.sanbuzhi.service.contenttyperel.ContentTypeRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentTypeserviceImpl implements ContentTypeService {
    @Autowired
    @Lazy
    private ContentService contentService;

    @Autowired
    private ContentTypeRelService contentTypeRelService;


    @Autowired
    private ContentTypeDao contentTypeDao;

    @Override
    public List<ContentTypeDomain> getAllTypes() {
        List<ContentTypeDomain> allCType = contentTypeDao.getAllCType();
        return allCType;
    }

    @Override
    public void subNumbers(Integer ctypeid) {
        ContentTypeDomain typeDomain = contentTypeDao.searchTypeById(ctypeid);
        System.out.println("typeDomain.getNumbers()"+typeDomain.getNumbers());
        if(typeDomain.getNumbers() > 1){
            //减一
            contentTypeDao.subNumber(ctypeid);
        }else{
            //如果这个类型的数量只剩一个，则直接删除类型
            contentTypeDao.deleteType(ctypeid);
        }
    }

    @Override
    public ContentTypeDomain getCTypeDomain(Integer ctypeid) {
        ContentTypeDomain contentTypeDomain = contentTypeDao.searchTypeById(ctypeid);
        return contentTypeDomain;
    }

    @Override
    public List<SpecialDomain> whatSpecialPageNeed() {
        List<ContentTypeDomain> allTypes = getAllTypes();
        ArrayList<SpecialDomain> specialDomains = new ArrayList<>();
        for(ContentTypeDomain contentTypeDomain: allTypes){
            Integer ctypeid = contentTypeDomain.getCtypeid();
            List<Integer> cids = contentTypeRelService.getCid(ctypeid);
            ArrayList<ContentDomain> contentDomains = new ArrayList<>();
            for(Integer cid: cids){
                ContentDomain article = contentService.getArticleById(cid);
                contentDomains.add(article);
            }
            SpecialDomain specialDomain = new SpecialDomain();
            specialDomain.setCTypeName(contentTypeDomain.getName());
            specialDomain.setContentDomainslist(contentDomains);
            specialDomains.add(specialDomain);
        }
        return specialDomains;
    }
}
