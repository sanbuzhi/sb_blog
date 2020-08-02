package com.sanbuzhi.controller;

import com.github.pagehelper.PageInfo;
import com.sanbuzhi.pojo.CommentDomain;
import com.sanbuzhi.pojo.ContentDomain;
import com.sanbuzhi.pojo_short.FileDomain;
import com.sanbuzhi.pojo_short.SpecialDomain;
import com.sanbuzhi.service.comment.CommentService;
import com.sanbuzhi.service.content.ContentService;
import com.sanbuzhi.service.contenttype.ContentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Controller
public class HomeController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ContentTypeService contentTypeService;


    @GetMapping(value = {"", "/index"})
    public String index(Model model, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return this.index(1, limit, model);
    }

    @RequestMapping("/tologin")
    public String tologin(){
        return "site/login";
    }

    @GetMapping(value = "/blog/page/{p}")
    public String index(
            @PathVariable(name = "p") int page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            Model model
    ){
        Map map = contentService.getRecentlyArticle(page, limit);
        //分页信息
        model.addAttribute("pageInfo", map.get("pageInfo"));
        //文章列表
        model.addAttribute("recentlyArticles", map.get("recentlyArticles"));
        return "site/index";
    }

    @RequestMapping("/blog/content/{pageindex}")
    public String blogcontent(@PathVariable("pageindex") Integer pageindex,Model model){
        ContentDomain articleById = contentService.getArticleById(pageindex);
        ContentDomain articleByIdpre = contentService.getArticleById(pageindex-1);
        ContentDomain articleByIdback = contentService.getArticleById(pageindex+1);
        model.addAttribute("article", articleById);
        model.addAttribute("articlepre", articleByIdpre);
        model.addAttribute("articleback", articleByIdback);
        //访问文章，点击数+1
        contentService.updateArticleHitCountById(pageindex);
        //得到评论列表
        List<CommentDomain> commentslist = commentService.getCommentsByCId(pageindex);
        System.out.println("commentslist+"+commentslist);
        model.addAttribute("comments", commentslist);
        return "site/pageindex";
    }

    @RequestMapping({"/special","/special/index"})
    public String special(Model model){
        List<SpecialDomain> specialDomains = contentTypeService.whatSpecialPageNeed();
        model.addAttribute("specialDomains", specialDomains);
        return "site/special";
    }

    @RequestMapping({"/file","/file/index"})
    public String file(Model model){
        List<FileDomain> fileDomains = contentService.whatFilePageNeed();
        model.addAttribute("fileDomains", fileDomains);
        return "site/file";
    }

}
