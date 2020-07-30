package com.sanbuzhi.controller.admin;

import com.sanbuzhi.service.content.ContentService;
import com.sanbuzhi.service.contenttype.ContentTypeService;
import com.sanbuzhi.service.contenttyperel.ContentTypeRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class IContentController {

    @Autowired
    private ContentService contentService;
    @Autowired
    private ContentTypeService contentTypeService;
    @Autowired
    private ContentTypeRelService contentTypeRelService;

    @RequestMapping("/del/{cid}")
    public String delArticle(@PathVariable("cid") Integer cid, Model model){
        System.out.println("cid+"+cid);
        contentService.deleteArticleById(cid);
        return "redirect:/admin/article_manage";
    }
}
