package com.sanbuzhi;

import com.github.pagehelper.PageInfo;
import com.sanbuzhi.dao.CommentDao;
import com.sanbuzhi.dao.ContentDao;
import com.sanbuzhi.dao.ContentTypeDao;
import com.sanbuzhi.dao.ContentTypeRelDao;
import com.sanbuzhi.pojo.CommentDomain;
import com.sanbuzhi.pojo.ContentDomain;
import com.sanbuzhi.pojo.ContentTypeDomain;
import com.sanbuzhi.pojo.UserDomain;
import com.sanbuzhi.pojo_short.ArticleTypeTag;
import com.sanbuzhi.service.comment.CommentService;
import com.sanbuzhi.service.content.ContentService;
import com.sanbuzhi.service.content.impl.ContentServiceImpl;
import com.sanbuzhi.service.contenttype.ContentTypeService;
import com.sanbuzhi.service.contenttyperel.ContentTypeRelService;
import com.sanbuzhi.service.user.UserService;
import com.sanbuzhi.service.user.impl.UserServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.css.Rect;

import javax.sound.midi.Receiver;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testuser(){
        SpringUtils utils = new SpringUtils();
        UserService userServiceImpl = (UserService) utils.getBean(UserServiceImpl.class);
        UserDomain user = userServiceImpl.getUserInfoById(1);
        System.out.println(user);
    }

    /*新增文章*/
    @Test
    public void testContent(){
        SpringUtils utils = new SpringUtils();
/*        ContentTypeDao bean = utils.getBean(ContentTypeDao.class);
        ContentTypeDomain contentTypeDomain = new ContentTypeDomain();
        contentTypeDomain.setName("类型1");
        contentTypeDomain.setNumbers(1);
        bean.addType(contentTypeDomain);*/
        ContentServiceImpl contentService = utils.getBean(ContentServiceImpl.class);
        for(int i = 0;i< 10;i++){
            ContentDomain contentDomain = new ContentDomain();
           /* contentDomain.setAllowComment(true);
            contentDomain.setAuthorId(111);
            contentDomain.setCid(124);
            contentDomain.setCommentsNum(0);*/
            contentDomain.setContent("这是内容1");
            /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String systime= sdf.format(new Date()).split(" ")[0];
            contentDomain.setCreated(systime);
            contentDomain.setClicks(0);
            contentDomain.setModified(null);*/
            contentDomain.setTitle("这是标题1");
            /*contentDomain.setTitlePic("这是图片链接");*/

            contentService.addArticle(contentDomain, "类型1", "标签1");
        }
    }

    /*删除文章*/
    @Test
    public void testContentSub(){
        SpringUtils utils = new SpringUtils();
        ContentServiceImpl contentService = utils.getBean(ContentServiceImpl.class);
        contentService.deleteArticleById(28);
    }

    /*获取最近文章*/
    @Test
    public void testRecentContent(){
        SpringUtils utils = new SpringUtils();
        ContentServiceImpl contentService = utils.getBean(ContentServiceImpl.class);
        PageInfo<ContentDomain> recentlyArticle = contentService.getRecentlyArticle(2, 5);
        int nextPage = recentlyArticle.getNextPage();
        System.out.println("nextpage->"+nextPage);
        System.out.println(recentlyArticle.getPages());

        System.out.println(recentlyArticle);
        System.out.println("-----");
        List<ContentDomain> list = recentlyArticle.getList();
        for(ContentDomain contentDomain:list){
            System.out.println(contentDomain);
        }
    }

    /*阅读量+1*/
    @Test
    public void test1(){
        SpringUtils utils = new SpringUtils();
        ContentServiceImpl contentService = utils.getBean(ContentServiceImpl.class);
        contentService.updateArticleHitCountById(36);
    }

    /*新增评论*/
    @Test
    public void test21(){
        SpringUtils utils = new SpringUtils();
        CommentDao bean = utils.getBean(CommentDao.class);
        CommentDomain commentDomain = new CommentDomain();
        for(int i=0;i<3;i++){
            commentDomain.setCid(36);
            commentDomain.setContent("好");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String systime= sdf.format(new Date()).split(" ")[0];
            commentDomain.setCreated(systime);
            commentDomain.setStatus("通过");
            bean.addComment(commentDomain);
        }
    }

    /*根据cid获得评论列表*/
    @Test
    public void test22(){
        SpringUtils utils = new SpringUtils();
        CommentService bean = utils.getBean(CommentService.class);
        List<CommentDomain> list = bean.getCommentsByCId(36);
        System.out.println("list+" +list);
    }

    /*制造文章*/
    @Test
    public void test31(){
        String head = "('春江花月夜','【作者】张若虚 春江潮水连海平，海上明月共潮生。','2007-01-01','',";
        String content = "春江潮水连海平，海上明月共潮生。滟滟随波千万里，何处春江无月明！江流宛转绕芳甸，月照花林皆似霰；空里流霜不觉飞，汀上白沙看不见。江天一色无纤尘，皎皎空中孤月轮。江畔何人初见月？江月何年初照人？人生代代无穷已，江月年年望相似。不知江月待何人，但见长江送流水。白云一片去悠悠，青枫浦上不胜愁。谁家今夜扁舟子？何处相思明月楼？可怜楼上月徘徊，应照离人妆镜台。玉户帘中卷不去，捣衣砧上拂还来。此时相望不相闻，愿逐月华流照君。鸿雁长飞光不度，鱼龙潜跃水成文。昨夜闲潭梦落花，可怜春半不还家。江水流春去欲尽，江潭落月复西斜。斜月沉沉藏海雾，碣石潇湘无限路。不知乘月几人归，落月摇情满江树。";
        String tail = ",'sanbuzhi','展示','0','0','1'),";
        String ss = head + content + tail + '\n';
        System.out.println(ss);
    }

    //获取所有文章+类型+标签
    @Test
    public void test12(){
        SpringUtils utils = new SpringUtils();
        ContentService bean = utils.getBean(ContentService.class);
        List<ArticleTypeTag> allArticleTypeTag = bean.getAllArticleTypeTag();
        System.out.println("1111");
        for(ArticleTypeTag articleTypeTag: allArticleTypeTag){
            System.out.println(articleTypeTag);
        }
    }

    //获取所有类型
    @Test
    public void test41(){
        SpringUtils utils = new SpringUtils();
        ContentTypeService bean = utils.getBean(ContentTypeService.class);
        List<ContentTypeDomain> allTypes = bean.getAllTypes();
        System.out.println(allTypes);
    }

    //文章类型-1
    @Test
    public void test42(){
        SpringUtils springUtils = new SpringUtils();
        ContentTypeRelService bean = springUtils.getBean(ContentTypeRelService.class);
        bean.deleteContentTypeRelByCid(156);
    }

}
