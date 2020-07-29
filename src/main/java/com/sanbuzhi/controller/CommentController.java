package com.sanbuzhi.controller;

import com.sanbuzhi.pojo.CommentDomain;
import com.sanbuzhi.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/addcomment",method = RequestMethod.POST)
    public String addcomment(CommentDomain commentDomain, Model model){
        //添加评论
        commentService.addComment(commentDomain);
        System.out.println("commentDomain+"+commentDomain);
        Integer cid = commentDomain.getCid();
        return "redirect:/blog/content/"+cid;
    }
}
