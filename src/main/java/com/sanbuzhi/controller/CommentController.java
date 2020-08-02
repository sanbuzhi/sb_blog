package com.sanbuzhi.controller;

import com.sanbuzhi.pojo.CommentDomain;
import com.sanbuzhi.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/addcomment",method = RequestMethod.POST)
    public String addcomment(CommentDomain commentDomain, Model model){
        /*添加评论时间*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String systime= sdf.format(new Date()).split(" ")[0];
        commentDomain.setCreated(systime);
        //添加评论
        commentService.addComment(commentDomain);
        System.out.println("commentDomain+"+commentDomain);
        Integer cid = commentDomain.getCid();
        return "redirect:/blog/content/"+cid;
    }
}
