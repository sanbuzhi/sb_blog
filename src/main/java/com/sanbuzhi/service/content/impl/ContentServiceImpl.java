package com.sanbuzhi.service.content.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sanbuzhi.constant.ErrorConstant;
import com.sanbuzhi.constant.WebConst;
import com.sanbuzhi.dao.*;
import com.sanbuzhi.exception.BusinessException;
import com.sanbuzhi.pojo.*;
import com.sanbuzhi.pojo_short.ArticleTTDomain;
import com.sanbuzhi.pojo_short.ArticleTypeTag;
import com.sanbuzhi.pojo_short.FileDomain;
import com.sanbuzhi.pojo_short.cond.ContentCond;
import com.sanbuzhi.service.content.ContentService;
import com.sanbuzhi.service.contenttag.ContentTagService;
import com.sanbuzhi.service.contenttagrel.ContentTagRelService;
import com.sanbuzhi.service.contenttype.ContentTypeService;
import com.sanbuzhi.service.contenttyperel.ContentTypeRelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    @Lazy
    private ContentTypeService contentTypeService;

    @Autowired
    @Lazy
    private ContentTypeRelService contentTypeRelService;

    @Autowired
    @Lazy
    private ContentTagService contentTagService;

    @Autowired
    @Lazy
    private ContentTagRelService contentTagRelService;

    //后续将dao层删掉
    @Autowired
    private ContentDao contentDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ContentTypeDao contentTypeDao;

    @Autowired
    private ContentTypeRelDao contentTypeRelDao;

    @Autowired
    private ContentTagDao contentTagDao;

    @Autowired
    private ContentTagRelDao contentTagRelDao;

    /**
     * 添加文章的同时
     * 修改和标签/分类的关联
     * 修改标签/分类下文章数量+1
     */
    @Transactional
    @Override
    @CacheEvict(value={"atricleCache","atricleCaches"},allEntries=true,beforeInvocation=true)
    public void addArticle(ContentDomain contentDomain,String type,String tag) {
        System.out.println("service->contentDomain+"+contentDomain);
        if (null == contentDomain)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        if (StringUtils.isBlank(contentDomain.getTitle()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_CAN_NOT_EMPTY);
        if (contentDomain.getTitle().length() > WebConst.MAX_TITLE_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_IS_TOO_LONG);
        if (StringUtils.isBlank(contentDomain.getContent()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_CAN_NOT_EMPTY);
        if (contentDomain.getContent().length() > WebConst.MAX_TEXT_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_IS_TOO_LONG);
        //添加文章
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String systime= sdf.format(new Date()).split(" ")[0];
        contentDomain.setCreated(systime);
        contentDao.addArticle(contentDomain);

        //分类字段不为空
        if(null != type){
            String[] splitedType = type.split(";");
            for(String ctypename : splitedType){
                //按名字从文章分类里找到此分类
                ContentTypeDomain contentTypeDomain = contentTypeDao.searchTypeByName(ctypename);
                if(null != contentTypeDomain){
                    //说明存在此分类，先给此类型的文章数量+1
                    contentTypeDao.addNumber(contentTypeDomain.getCtypeid());
                    // 再往关联表添加关联
                    ContentTypeRelDomain contentTypeRelDomain = new ContentTypeRelDomain();
                    contentTypeRelDomain.setCid(contentDomain.getCid());
                    contentTypeRelDomain.setCtypeid(contentTypeDomain.getCtypeid());
                    contentTypeRelDao.addContentTypeRel(contentTypeRelDomain);
                }else {
                    //说明文章分类里没有此分类，则先创建此分类
                    ContentTypeDomain contentTypeDomain1 = new ContentTypeDomain();
                    contentTypeDomain1.setName(ctypename);//这里可以不用设置ctypeid
                    contentTypeDomain1.setNumbers(1);
                    contentTypeDao.addType(contentTypeDomain1);
                    // 再往关联表添加关联
                    ContentTypeRelDomain contentTypeRelDomain = new ContentTypeRelDomain();
                    contentTypeRelDomain.setCid(contentDomain.getCid());
                    ContentTypeDomain tmpCTD = contentTypeDao.searchTypeByName(ctypename);
                    contentTypeRelDomain.setCtypeid(tmpCTD.getCtypeid());
                    contentTypeRelDao.addContentTypeRel(contentTypeRelDomain);
                }
            }
        }
        //标签字段不为空
        if(null != tag){
            String[] splitedTag = tag.split(";");
            for(String ctagname : splitedTag){
                //按名字从文章标签里找到此标签
                ContentTagDomain contentTagDomain = contentTagDao.searchTagByName(ctagname);
                if(null != contentTagDomain){
                    //说明存在此分类，先给此类型的文章数量+1
                    contentTagDao.addNumber(contentTagDomain.getCtagid());
                    // 再往关联表添加关联
                    ContentTagRelDomain contentTagRelDomain = new ContentTagRelDomain();
                    contentTagRelDomain.setCid(contentDomain.getCid());
                    contentTagRelDomain.setCtagid(contentTagDomain.getCtagid());
                    contentTagRelDao.addContentTagRel(contentTagRelDomain);
                }else {
                    //说明文章分类里没有此分类，则先创建此分类
                    ContentTagDomain contentTagDomain1 = new ContentTagDomain();
                    contentTagDomain1.setName(ctagname);
                    contentTagDomain1.setNumbers(1);
                    contentTagDao.addTag(contentTagDomain1);
                    // 再往关联表添加关联
                    ContentTagRelDomain contentTagRelDomain = new ContentTagRelDomain();
                    contentTagRelDomain.setCid(contentDomain.getCid());
                    ContentTagDomain tmpCTD = contentTagDao.searchTagByName(ctagname);
                    contentTagRelDomain.setCtagid(tmpCTD.getCtagid());
                    contentTagRelDao.addContentTagRel(contentTagRelDomain);
                }
            }
        }
    }


    /**
     *  删除文章的同时
     *  删除文章下所有评论
     *  删除和标签/分类的关联
     *  修改标签/分类下的文章数量-1
     */
    @Override
    @Transactional
    @CacheEvict(value={"atricleCache","atricleCaches"},allEntries=true,beforeInvocation=true)
    public void deleteArticleById(Integer cid) {
        if (null == cid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        contentDao.deleteArticleById(cid);
        //同时文章下的类型数量-1，如果为0，则删除类型
        List<Integer> ctypeids = contentTypeRelService.getCtypeid(cid);
        System.out.println("ctypeids+"+ctypeids.toString());
        for(Integer ct: ctypeids){
            contentTypeService.subNumbers(ct);
        }
        //最后删除文章类型关联表中cid对应的所有项
        contentTypeRelService.deleteContentTypeRelByCid(cid);
    }

    /**
     * 文章阅读量+1
     */
    @Override
    public void updateArticleHitCountById(Integer cid) {
        contentDao.updateArticleHitCountById(cid);
    }

    @Override
    public void updateArticleCommentCountById(Integer cid) {
        contentDao.updateArticleCommentCountById(cid);
    }


    /**
     * 根据id获取文章
     */
    @Override
    @Cacheable(value = "atricleCache", key = "'atricleById_' + #p0")
    public ContentDomain getArticleById(Integer cid) {
        if (null == cid)
            return null;
        return contentDao.getArticleById(cid);
    }

    /**
     * 根据条件获取文章分页信息
     */
    @Override
    @Cacheable(value = "atricleCaches", key = "'articlesByCond_' + #p1 + 'type_' + #p0.type")
    public PageInfo<ContentDomain> getArticlesByCond(ContentCond contentCond, int pageNum, int pageSize) {
        if (null == contentCond)
            return null;
        PageHelper.startPage(pageNum, pageSize);
        List<ContentDomain> contents = contentDao.getArticlesByCond(contentCond);
        PageInfo<ContentDomain> pageInfo = new PageInfo<>(contents);

        return pageInfo;
    }

    /**
     * 获取最近的文章的分页信息
     */
    @Override
    @Cacheable(value = "atricleCaches", key = "'recentlyArticle_' + #p0")
    public Map getRecentlyArticle(Integer pageNum, Integer pageSize) {
        Map<Object, Object> hashMap = new HashMap<>();
        //为了程序的严谨性，判断非空：
        //设置默认当前页
        if(pageNum==null || pageNum<=0){
            pageNum = 1;
        }
        //设置默认每页显示的数据数
        if(pageSize == null){
            pageSize = 12;
        }
        PageHelper.startPage(pageNum, pageSize);
        try{
            List<ContentDomain> recentlyArticles = contentDao.getRecentlyArticle();
            PageInfo<ContentDomain> pageInfo = new PageInfo<>(recentlyArticles,pageSize);
            hashMap.put("pageInfo", pageInfo);
            hashMap.put("recentlyArticles", recentlyArticles);
        }finally {
            PageHelper.clearPage();
        }

        return hashMap;
    }

    @Override
    public List<ContentDomain> getAllArticle() {
        List<ContentDomain> articles = contentDao.getRecentlyArticle();
        return articles;
    }

    @Override
    public List<ArticleTypeTag> getAllArticleTypeTag() {
        List<ContentDomain> articles = this.getAllArticle();
        List<ArticleTypeTag> articleTypeTags = new ArrayList<>();
        for(ContentDomain contentDomain: articles){
            //将文章实体+对应类型拼接字符串+对应标签拼接字符串封装进ArticleTypeTag实体
            ArticleTypeTag articleTypeTag = new ArticleTypeTag();
            articleTypeTag.setContentDomain(contentDomain);//注入文章实体
            //文章cid->关系表找到对应ctypeid列表->类型表找到类型实体列表
            List<Integer> ctypeid = contentTypeRelDao.getCtypeid(contentDomain.getCid());
            String strtypes = "";
            for(Integer integer : ctypeid){
                ContentTypeDomain contentTypeDomain = contentTypeDao.searchTypeById(integer);
                strtypes += contentTypeDomain.getName()+" ";
            }
            articleTypeTag.setTypes(strtypes);//注入类型拼接成的字符串
            //文章tag一样
            List<Integer> ctagid = contentTagRelDao.getCtagid(contentDomain.getCid());
            String strtags = "";
            for(Integer integer : ctagid){
                ContentTagDomain contentTagDomain = contentTagDao.searchTagById(integer);
                strtags += contentTagDomain.getName() + " ";
            }
            articleTypeTag.setTags(strtags);//注入标签拼接城的字符串
            articleTypeTags.add(articleTypeTag);
        }
        return articleTypeTags;
    }


    /**
     * 获取查找到的文章的分页信息
     */
    @Override
    public PageInfo<ContentDomain> searchArticle(String param, int pageNun, int pageSize) {
        PageHelper.startPage(pageNun,pageSize);
        List<ContentDomain> contentDomains = contentDao.searchArticle(param);
        PageInfo<ContentDomain> pageInfo = new PageInfo<>(contentDomains);
        return pageInfo;
    }

    /**
     * 通过cid返回一个map{文章实体类+对应类型+对应标签}
     * 更改文章时需要
     * 还需要清空文章的类型标签等信息
     */
    @Override
    public Map ContentUpdateEdit(Integer cid) {
        Map<Object, Object> hashMap = new HashMap<>();

        ContentDomain contentDomain = this.getArticleById(cid);
        //获得cid对应的所有type和tag
        List<Integer> ctypeids = contentTypeRelService.getCtypeid(cid);
        String types = "";
        for(Integer ct: ctypeids){
            ContentTypeDomain cTypeDomain = contentTypeService.getCTypeDomain(ct);
            types = types + cTypeDomain.getName() + ";";
            //删除此标签的关联
            contentTypeRelService.deleteContentTypeRelByCtypeid(ct);
            //标签数量-1
            contentTypeService.subNumbers(ct);
        }
        List<Integer> cTagids = contentTagRelService.getCTagidByCid(cid);
        String tags = "";
        for(Integer ctag: cTagids){
            ContentTagDomain contentTagDomain = contentTagService.getCTagDomainByCtagid(ctag);
            tags = tags+contentTagDomain.getName() + ";";
            //删除此标签的关联
            contentTagRelService.deleteContentTagRelByCtagid(ctag);
            //标签数量-1
            contentTagService.subNumbers(ctag);
        }
        hashMap.put("contentDomain", contentDomain);
        hashMap.put("types", types);
        hashMap.put("tags", tags);
        return hashMap;
    }

    @Override
    public void updateArticle(ContentDomain contentDomain, String type, String tag) {
        if (null == contentDomain)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        if (StringUtils.isBlank(contentDomain.getTitle()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_CAN_NOT_EMPTY);
        if (contentDomain.getTitle().length() > WebConst.MAX_TITLE_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_IS_TOO_LONG);
        if (StringUtils.isBlank(contentDomain.getContent()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_CAN_NOT_EMPTY);
        if (contentDomain.getContent().length() > WebConst.MAX_TEXT_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_IS_TOO_LONG);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String systime= sdf.format(new Date()).split(" ")[0];
        contentDomain.setModified(systime);
        System.out.println("contentDomain+++" + contentDomain);
        contentDao.updateArticleById(contentDomain);
        //分类字段不为空
        if(null != type){
            String[] splitedType = type.split(";");
            for(String ctypename : splitedType){
                //按名字从文章分类里找到此分类
                ContentTypeDomain contentTypeDomain = contentTypeDao.searchTypeByName(ctypename);
                if(null != contentTypeDomain){
                    //说明存在此分类，先给此类型的文章数量+1
                    contentTypeDao.addNumber(contentTypeDomain.getCtypeid());
                    // 再往关联表添加关联
                    ContentTypeRelDomain contentTypeRelDomain = new ContentTypeRelDomain();
                    contentTypeRelDomain.setCid(contentDomain.getCid());
                    contentTypeRelDomain.setCtypeid(contentTypeDomain.getCtypeid());
                    contentTypeRelDao.addContentTypeRel(contentTypeRelDomain);
                }else {
                    //说明文章分类里没有此分类，则先创建此分类
                    ContentTypeDomain contentTypeDomain1 = new ContentTypeDomain();
                    contentTypeDomain1.setName(ctypename);//这里可以不用设置ctypeid
                    contentTypeDomain1.setNumbers(1);
                    contentTypeDao.addType(contentTypeDomain1);
                    // 再往关联表添加关联
                    ContentTypeRelDomain contentTypeRelDomain = new ContentTypeRelDomain();
                    contentTypeRelDomain.setCid(contentDomain.getCid());
                    ContentTypeDomain tmpCTD = contentTypeDao.searchTypeByName(ctypename);
                    contentTypeRelDomain.setCtypeid(tmpCTD.getCtypeid());
                    contentTypeRelDao.addContentTypeRel(contentTypeRelDomain);
                }
            }
        }
        //标签字段不为空
        if(null != tag) {
            String[] splitedTag = tag.split(";");
            for (String ctagname : splitedTag) {
                //按名字从文章标签里找到此标签
                ContentTagDomain contentTagDomain = contentTagDao.searchTagByName(ctagname);
                if (null != contentTagDomain) {
                    //说明存在此分类，先给此类型的文章数量+1
                    contentTagDao.addNumber(contentTagDomain.getCtagid());
                    // 再往关联表添加关联
                    ContentTagRelDomain contentTagRelDomain = new ContentTagRelDomain();
                    contentTagRelDomain.setCid(contentDomain.getCid());
                    contentTagRelDomain.setCtagid(contentTagDomain.getCtagid());
                    contentTagRelDao.addContentTagRel(contentTagRelDomain);
                } else {
                    //说明文章分类里没有此分类，则先创建此分类
                    ContentTagDomain contentTagDomain1 = new ContentTagDomain();
                    contentTagDomain1.setName(ctagname);
                    contentTagDomain1.setNumbers(1);
                    contentTagDao.addTag(contentTagDomain1);
                    // 再往关联表添加关联
                    ContentTagRelDomain contentTagRelDomain = new ContentTagRelDomain();
                    contentTagRelDomain.setCid(contentDomain.getCid());
                    ContentTagDomain tmpCTD = contentTagDao.searchTagByName(ctagname);
                    contentTagRelDomain.setCtagid(tmpCTD.getCtagid());
                    contentTagRelDao.addContentTagRel(contentTagRelDomain);
                }
            }
        }
    }

    @Override
    public Long getArticleCount() {
        Long articleCount = contentDao.getArticleCount();
        return articleCount;
    }

    @Override
    public List<FileDomain> whatFilePageNeed() {
        ArrayList<FileDomain> fileDomains = new ArrayList<>();
        List<ContentDomain> articles = this.getAllArticle();
        Set<String> sets = new HashSet<>();//用来存放"年月"的可能
        for(ContentDomain contentDomain : articles){
            String yearMonth = contentDomain.getCreated().split("-")[0] + "年" + contentDomain.getCreated().split("-")[1] + "月";
            sets.add(yearMonth);
        }
        for(String ss : sets){
            FileDomain fileDomain = new FileDomain();
            fileDomain.setYearMonth(ss);
            //先设置yearMonth，再设置List<ArticleTypeTag>
            ArrayList<ArticleTypeTag> articleTypeTags = new ArrayList<>();
            for(ContentDomain contentDomain : articles){
                String yearMonth = contentDomain.getCreated().split("-")[0] + "年" + contentDomain.getCreated().split("-")[1] + "月";
                if(ss.equals(yearMonth)){
                    ArticleTypeTag articleTypeTag = new ArticleTypeTag();
                    List<Integer> cTagids = contentTagRelService.getCTagidByCid(contentDomain.getCid());
                    String s = new String();
                    for(Integer ctagid : cTagids){
                        String tagDomainname = contentTagService.getCTagDomainByCtagid(ctagid).getName();
                        s = s + tagDomainname + ' ';
                    }
                    articleTypeTag.setTags(s);
                    articleTypeTag.setContentDomain(contentDomain);
                    articleTypeTags.add(articleTypeTag);
                }
            }
            fileDomain.setArticleTypeTagList(articleTypeTags);
            fileDomains.add(fileDomain);
        }
        return fileDomains;
    }
}
