package com.sanbuzhi.controller.admin;

import com.sanbuzhi.service.comment.CommentService;
import com.sanbuzhi.service.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ICommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/comm_del/{coid}")
    public String delcomm(@PathVariable("coid") Integer coid){
        commentService.deleteComment(coid);
        return "redirect:/admin/comment_manage";
    }

    @RequestMapping("/comm_update/{coid}")
    public String updatecomm(@PathVariable("coid") Integer coid){
        commentService.changeCommentStatus(coid);
        return "redirect:/admin/comment_manage";
    }
}
