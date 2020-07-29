package com.sanbuzhi.controller;

import com.github.pagehelper.PageInfo;
import com.sanbuzhi.pojo.CommentDomain;
import com.sanbuzhi.pojo.ContentDomain;
import com.sanbuzhi.service.comment.CommentService;
import com.sanbuzhi.service.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private CommentService commentService;

    @GetMapping(value = {"", "/index"})
    public String index(Model model, @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return this.index(1, limit, model);
    }

    @GetMapping(value = "/blog/page/{p}")
    public String index(
            @PathVariable(name = "p") int page,
            @RequestParam(name = "limit", required = false, defaultValue = "5") int limit,
            Model model
    ){
        PageInfo<ContentDomain> articles = contentService.getRecentlyArticle(page, limit);
        model.addAttribute("articles", articles);
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
}
