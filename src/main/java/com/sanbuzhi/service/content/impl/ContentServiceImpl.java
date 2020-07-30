package com.sanbuzhi.service.content.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sanbuzhi.constant.ErrorConstant;
import com.sanbuzhi.constant.WebConst;
import com.sanbuzhi.dao.*;
import com.sanbuzhi.exception.BusinessException;
import com.sanbuzhi.pojo.*;
import com.sanbuzhi.pojo_short.ArticleTypeTag;
import com.sanbuzhi.pojo_short.cond.ContentCond;
import com.sanbuzhi.service.content.ContentService;
import com.sanbuzhi.service.contenttype.ContentTypeService;
import com.sanbuzhi.service.contenttyperel.ContentTypeRelService;
import com.sun.xml.internal.ws.encoding.ContentType;
import com.sun.xml.internal.ws.encoding.ContentTypeImpl;
import net.bytebuddy.implementation.bind.ArgumentTypeResolver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentTypeService contentTypeService;

    @Autowired
    private ContentTypeRelService contentTypeRelService;

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
    public PageInfo<ContentDomain> getRecentlyArticle(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ContentDomain> recentlyArticle = contentDao.getRecentlyArticle();
        //时间戳格式化
        PageInfo<ContentDomain> pageInfo = new PageInfo<>(recentlyArticle);
        System.out.println("PageInfo:"+pageInfo);
        return pageInfo;
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
     * 文章阅读量+1
     */

}
