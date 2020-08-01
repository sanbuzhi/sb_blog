package com.sanbuzhi.pojo_short;

import com.sanbuzhi.pojo.ContentDomain;
import lombok.Data;

import java.util.List;

@Data
public class SpecialDomain {
    private String cTypeName;
    private List<ContentDomain> contentDomainslist;
}
