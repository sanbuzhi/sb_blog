package com.sanbuzhi.controller.admin;

import com.sanbuzhi.dao.ContentDao;
import com.sanbuzhi.pojo.CommentDomain;
import com.sanbuzhi.pojo.ContentDomain;
import com.sanbuzhi.pojo.ContentTagDomain;
import com.sanbuzhi.pojo.ContentTypeDomain;
import com.sanbuzhi.pojo_short.ArticleTypeTag;
import com.sanbuzhi.service.comment.CommentService;
import com.sanbuzhi.service.content.ContentService;
import com.sanbuzhi.service.contenttag.ContentTagService;
import com.sanbuzhi.service.contenttype.ContentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class IndexController {
    @Autowired
    private ContentService contentService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ContentTypeService contentTypeService;

    @Autowired
    private ContentTagService contentTagService;


    //1.网站统计
    @RequestMapping({"","/index"})
    public String index(Model model){
        Long articleCount = contentService.getArticleCount();
        Long commentsCount = commentService.getCommentsCount();
        model.addAttribute("articleCount", articleCount);
        model.addAttribute("commentsCount", commentsCount);

        return "admin/index";
    }

    //2.编辑文章
    @RequestMapping("/article_edit")
    public String editContent(){
        return "admin/article_edit";
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

    //3.文章管理
    @RequestMapping("/article_manage")
    public String artmanage(Model model){
        List<ArticleTypeTag> allArticleTypeTags = contentService.getAllArticleTypeTag();
        model.addAttribute("allArticleTypeTags", allArticleTypeTags);
        //待补充
        return "admin/article_manage";
    }

    //4.评论管理
    @RequestMapping("/comment_manage")
    public String commmanage(Model model){
        List<CommentDomain> allComments = commentService.getAllComments();
        model.addAttribute("allComments", allComments);
        return "admin/comment_manage";
    }

    //5.分类/标签管理
    @RequestMapping("/typetag_manage")
    public String typetagmanage(Model model){
        List<ContentTypeDomain> allTypes = contentTypeService.getAllTypes();
        List<ContentTagDomain> allTags = contentTagService.getAllTags();
        model.addAttribute("allTypes", allTypes);
        model.addAttribute("allTags", allTags);
        return "admin/typetag_manage";
    }
}
