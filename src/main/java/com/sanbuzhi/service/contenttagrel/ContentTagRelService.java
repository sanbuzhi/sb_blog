package com.sanbuzhi.service.contenttagrel;

import com.sanbuzhi.pojo.ContentTagRelDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentTagRelService {
    List<Integer> getCTagidByCid(Integer cid);

    void deleteContentTagRelByCtagid(@Param("ctagid") Integer ctagid);
}
