package com.sanbuzhi.controller.admin;

import com.sanbuzhi.pojo.ContentDomain;
import com.sanbuzhi.service.content.ContentService;
import com.sanbuzhi.service.contenttag.ContentTagService;
import com.sanbuzhi.service.contenttagrel.ContentTagRelService;
import com.sanbuzhi.service.contenttype.ContentTypeService;
import com.sanbuzhi.service.contenttyperel.ContentTypeRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
@RequestMapping("/admin")
public class IContentController {

    @Autowired
    private ContentService contentService;
    @Autowired
    private ContentTypeService contentTypeService;
    @Autowired
    private ContentTypeRelService contentTypeRelService;
    @Autowired
    private ContentTagService contentTagService;
    @Autowired
    private ContentTagRelService contentTagRelService;

    @RequestMapping("/arti_del/{cid}")
    public String delArticle(@PathVariable("cid") Integer cid){
        System.out.println("cid+"+cid);
        contentService.deleteArticleById(cid);
        return "redirect:/admin/article_manage";
    }

    /*跳转到修改页面*/
    @RequestMapping("/arti_update/{cid}")
    public String updateArticle(@PathVariable("cid") Integer cid,Model model){
        Map map = contentService.ContentUpdateEdit(cid);
        model.addAttribute("contentDomain", map.get("contentDomain"));
        model.addAttribute("types", map.get("types"));
        model.addAttribute("tags", map.get("tags"));
        return "admin/article_editUpdate";
    }

    /*编辑完成返回的页面*/
    @RequestMapping("/submitPage_update")
    public String submitPageupdate(ContentDomain contentDomain,
                                   @RequestParam(value = "type",defaultValue = "null",required = false) String type,
                                   @RequestParam(value = "tag",defaultValue = "null",required = false) String tag
    ){
        System.out.println("contentDomain+" + contentDomain);
        System.out.println("type+" + type);
        System.out.println("tag+" + tag);
        /*给内容添加其他属性*/
        contentService.updateArticle(contentDomain, type, tag);
        return "redirect:/admin/article_manage";
    }

}
