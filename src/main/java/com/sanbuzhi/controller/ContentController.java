package com.sanbuzhi.controller;

import com.sanbuzhi.pojo.ContentDomain;
import com.sanbuzhi.service.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    //进入编辑页面edit.html
    @RequestMapping("/edit")
    public String editContent(){
        return "edit";
    }
    //编辑完成，返回后台主页！
    @RequestMapping(value = "/submitPage",method = RequestMethod.POST)
    public String submitPage(ContentDomain contentDomain,
                             @RequestParam(value = "type",defaultValue = "null",required = false) String type,
                             @RequestParam(value = "tag",defaultValue = "null",required = false) String tag
                             ){
        System.out.println("contentDomain+" + contentDomain);
        System.out.println("type+" + type);
        System.out.println("tag+" + tag);
        /*给内容添加其他属性*/
        contentService.addArticle(contentDomain, type, tag);

        return "admin/index";
    }

}
