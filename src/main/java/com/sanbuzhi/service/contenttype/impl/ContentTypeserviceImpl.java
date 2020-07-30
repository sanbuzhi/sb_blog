package com.sanbuzhi.service.contenttype.impl;

import com.sanbuzhi.dao.ContentTypeDao;
import com.sanbuzhi.pojo.ContentTypeDomain;
import com.sanbuzhi.service.contenttype.ContentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentTypeserviceImpl implements ContentTypeService {
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
}
