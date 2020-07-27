package com.sanbuzhi;

import com.github.pagehelper.PageInfo;
import com.sanbuzhi.dao.ContentDao;
import com.sanbuzhi.dao.ContentTypeDao;
import com.sanbuzhi.pojo.ContentDomain;
import com.sanbuzhi.pojo.ContentTypeDomain;
import com.sanbuzhi.pojo.UserDomain;
import com.sanbuzhi.service.content.impl.ContentServiceImpl;
import com.sanbuzhi.service.user.UserService;
import com.sanbuzhi.service.user.impl.UserServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.css.Rect;

import javax.sound.midi.Receiver;
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
            contentDomain.setAllowComment(true);
            contentDomain.setAuthorId(111);
            contentDomain.setCid(124);
            contentDomain.setCommentsNum(0);
            contentDomain.setContent("这是内容1");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String systime= sdf.format(new Date()).split(" ")[0];
            contentDomain.setCreated(systime);
            contentDomain.setClicks(0);
            contentDomain.setModified(null);
            contentDomain.setTitle("这是标题1");
            contentDomain.setTitlePic("这是图片链接");

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
}
