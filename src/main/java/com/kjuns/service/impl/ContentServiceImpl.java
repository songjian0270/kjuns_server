package com.kjuns.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kjuns.mapper.CampMapper;
import com.kjuns.mapper.CommentMapper;
import com.kjuns.mapper.ContentMapper;
import com.kjuns.mapper.ContentRelatedArticlesMapper;
import com.kjuns.mapper.ContentTagMapper;
import com.kjuns.mapper.ContentTypeMapper;
import com.kjuns.mapper.SectionMapper;
import com.kjuns.mapper.UserInfoMapper;
import com.kjuns.model.Content;
import com.kjuns.model.ContentRelatedArticles;
import com.kjuns.model.ContentSection;
import com.kjuns.model.ContentTag;
import com.kjuns.model.ContentType;
import com.kjuns.model.PageList;
import com.kjuns.model.UserInfo;
import com.kjuns.out.BaseOutJB;
import com.kjuns.service.ContentService;
import com.kjuns.util.CommonConstants;
import com.kjuns.util.CommonUtils;
import com.kjuns.util.ErrorCode;
import com.kjuns.util.SysConf;
import com.kjuns.util.UUIDUtils;
import com.kjuns.util.pager.Page;
import com.kjuns.vo.ContentVo;

/**
 * <b>Function: </b>
 * 
 * @author James
 * @date 2015-9-6
 * @file ContentServiceImpl.java
 * @package com.kjuns.service.impl
 * @project kjuns
 * @version 2.0
 */
@Service("contentService")
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private ContentTypeMapper contentTypeMapper;
	
	@Autowired
	private ContentMapper contentMapper;
	
	@Autowired
	private SectionMapper sectionMapper;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private ContentTagMapper contentTagMapper;
	
	@Autowired
	private ContentRelatedArticlesMapper contentRelatedArticlesMapper;
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private CampMapper campMapper;

	@Override
	public PageList queryContent(String typeId, String userId, Page page) throws Exception {
		String isAdmin = "false";
		if(CommonUtils.notEmpty(userId)){
			if(userId.indexOf("admin") > 0){
				isAdmin = "true";
			}
		}
		List<ContentVo> contentList = new ArrayList<>();
		PageList pageList = new PageList();
		int count = contentMapper.getTotalCount(isAdmin, typeId, null);
		if(count > 0){
			page.setTotalCount(count);
			int number = page.getPageSize() / SysConf.INTERVAL_NUMBER;	
			System.out.println(number);
			List<Content> list =  contentMapper.queryContentList(isAdmin, typeId, null, page.getStart(), page.getPageSize() - number);
			if(CommonUtils.notListFEmpty(list)){
				int i = 0; int k = 1 ;int j = 1;
				for(Content content: list){
					if(i == SysConf.INTERVAL_NUMBER - 1){
						int pageNumber =  page.getStart()/page.getPageSize();
						List<ContentSection> sectionList = sectionMapper.queryContentSectionList( pageNumber * number , number);
						if(CommonUtils.notListFEmpty(sectionList)){
							for(ContentSection section: sectionList){
								if(k == j){
									ContentVo contents = new ContentVo();
									contents.setId(section.getId());
									contents.setTitle(section.getTitle());
									contents.setSummary(section.getSummary());
									contents.setThumbnail(CommonUtils.getImage(section.getThumbnail()));
									contents.setType(1);
									int commentCount = commentMapper.getTotalCount(CommonConstants.KJUNS_CONTENT_COMMENTS, content.getId(), null);
									contents.setCommentCount(commentCount);
									if(CommonUtils.notEmpty(section.getId()) && CommonUtils.notEmpty(userId)){
										int c  = sectionMapper.getRssCount(userId, section.getId());
										contents.setIsRss(c > 0 ? 0 : 1);
									}else if(CommonUtils.notEmpty(section.getId()) ){
										int c  = sectionMapper.getRssCount(null, section.getId());
										contents.setRssCount(c);
									}
									List<Content> sectionContentList = contentMapper.queryContentList(isAdmin, null, section.getId(), 0, 6);
									List<ContentVo> ls = new ArrayList<>();
									for(Content sectionContent: sectionContentList){
										ContentVo c = new ContentVo();
										c.setId(sectionContent.getId());
										c.setTitle(sectionContent.getTitle());
										c.setSummary(sectionContent.getSummary());
										c.setThumbnail(CommonUtils.getImage(content.getThumbnail()));
										c.setLikeCount(sectionContent.getLikeCount());
										c.setShareCount(sectionContent.getShareCount());
										UserInfo userInfo = userInfoMapper.get(sectionContent.getUserId());
										c.setIssuerFaceSrc(CommonUtils.getImage(userInfo.getFaceSrc()));
										c.setIssuerName(userInfo.getNickName());
										c.setCreateDate(CommonUtils.dateToUnixTimestamp(sectionContent.getCreateDate(), 
													CommonConstants.DATETIME_SEC));
										int cc = commentMapper.getTotalCount(CommonConstants.KJUNS_CONTENT_COMMENTS, sectionContent.getId(), null);
										c.setCommentCount(cc);
										ls.add(c);
									}
									contents.setContentList(ls);
									UserInfo userInfo = userInfoMapper.get(section.getUserId());
									contents.setIssuerFaceSrc(CommonUtils.getImage(userInfo.getFaceSrc()));
									contents.setIssuerName(userInfo.getNickName());
									contents.setCreateDate(CommonUtils.dateToUnixTimestamp(section.getCreateDate(), 
												CommonConstants.DATETIME_SEC));
									contentList.add(contents);
								}
								k++;
							}
						}
						j++;
						i = 0;
					}else{
						ContentVo contents = new ContentVo();
						contents.setId(content.getId());
						contents.setTitle(content.getTitle());
						contents.setSummary(content.getSummary());
						contents.setType(0);
						contents.setThumbnail(CommonUtils.getImage(content.getThumbnail()));
						contents.setLikeCount(content.getLikeCount());
						contents.setShareCount(content.getShareCount());
						UserInfo userInfo = userInfoMapper.get(content.getUserId());
						contents.setIssuerFaceSrc(CommonUtils.getImage(userInfo.getFaceSrc()));
						contents.setIssuerName(userInfo.getNickName());
						contents.setCreateDate(CommonUtils.dateToUnixTimestamp(content.getCreateDate(), 
									CommonConstants.DATETIME_SEC));
						int cc = commentMapper.getTotalCount(CommonConstants.KJUNS_CONTENT_COMMENTS, content.getId(), null);
						contents.setCommentCount(cc);
						contentList.add(contents);
					}
					i++;
				}
			}
		}
		pageList.setPageInvertedIndex(page.getReturnIndex());
		pageList.setTotalCount(count);
		pageList.setList(contentList);
		return pageList;
	}

	@Override
	public BaseOutJB insertCamp(Content content) throws Exception {
		String datetime = CommonConstants.DATETIME_SEC.format(new Date());
		content.setCreateDate(datetime);
		String id = UUIDUtils.getUUID().toString().replace("-", "");
		content.setId(id);
		boolean isSucceed = campMapper.insertCamp(content) > 0 ? true: false;
		if(isSucceed){
			return new BaseOutJB(ErrorCode.SUCCESS);
		}else{
			return new BaseOutJB(ErrorCode.FAILED);
		}
	}

	@Override
	public BaseOutJB delContentById(String id, String userId) {
		String datetime = CommonConstants.DATETIME_SEC.format(new Date());
		Content Content = new Content();
		Content.setId(id);
		Content.setUpdateBy(userId);
		Content.setUpdateDate(datetime);
		boolean isSucceed = contentMapper.delContentById(Content) > 0 ? true: false;
		if(isSucceed){
			return new BaseOutJB(ErrorCode.SUCCESS);
		}else{
			return new BaseOutJB(ErrorCode.FAILED);
		}
	}

	@Override
	public List<ContentType> queryContentType() throws Exception {
		return contentTypeMapper.queryContentType();
	}

	@Override
	public ContentVo selectById(String id) throws Exception {
		ContentVo contentVo = contentMapper.selectById(id);
		//标签
		List<ContentTag> tagList = contentTagMapper.queryContentTagForContentId(id);
		if(null != tagList){
			contentVo.setContentTagList(tagList);	
		}
		//相关推荐
		List<ContentRelatedArticles> relatedArticlesList = 
				contentRelatedArticlesMapper.queryContentRelatedArticlesForContentId(id);
		if(null != relatedArticlesList){
			contentVo.setContentRelatedArticlesList(relatedArticlesList);
		}
		//发布人
		if(CommonUtils.notEmpty(contentVo.getIssuerId())){
			UserInfo userInfo = userInfoMapper.get(contentVo.getIssuerId());
			contentVo.setIssuerId(contentVo.getIssuerId());
			contentVo.setIssuerName(userInfo.getNickName());
			contentVo.setIssuerFaceSrc(CommonUtils.getImage(userInfo.getFaceSrc()));
		}
		if(CommonUtils.notEmpty(contentVo.getDateTime())){
			contentVo.setCreateDate(CommonUtils.dateToUnixTimestamp(contentVo.getDateTime(), 
					CommonConstants.DATETIME_SEC));
		}
		if(CommonUtils.notEmpty(contentVo.getMindMap())){
			contentVo.setMindMap(CommonUtils.getImage(contentVo.getMindMap()));
		}
		if(CommonUtils.notEmpty(contentVo.getThumbnail())){
			contentVo.setThumbnail(CommonUtils.getImage(contentVo.getThumbnail()));
		}
		return contentVo;
	}

	@Override
	public ErrorCode insertContentLike(String contentId) throws Exception {
		ErrorCode errorCode = contentMapper.insertContentLike(contentId) > 0 ? ErrorCode.SUCCESS : ErrorCode.FAILED;
		return errorCode;
	}

	@Override
	public ErrorCode insertContentShare(String contentId) throws Exception {
		ErrorCode errorCode = contentMapper.insertContentShare(contentId) > 0 ? ErrorCode.SUCCESS : ErrorCode.FAILED;
		return errorCode;
	}

	@Override
	public PageList querySectionContent(String sectionId, String userId, Page page) throws Exception {
		String isAdmin = "false";
		if(CommonUtils.notEmpty(userId)){
			if(userId.indexOf("admin") > 0){
				isAdmin = "true";
			}
		}
		List<ContentVo> contentList = new ArrayList<>();
		int count = contentMapper.getTotalCount(isAdmin, null, sectionId);
		PageList pageList = new PageList();
		if(count > 0){
			page.setTotalCount(count);
			List<Content> contents = contentMapper.queryContentList(isAdmin, null, sectionId, page.getStart(), page.getPageSize());
			for(Content content:contents){
				ContentVo contentv = new ContentVo();
				contentv.setId(content.getId());
				contentv.setTitle(content.getTitle());
				contentv.setSummary(content.getSummary());
				contentv.setType(1);
				contentv.setThumbnail(CommonUtils.getImage(content.getThumbnail()));
				contentv.setLikeCount(content.getLikeCount());
				contentv.setShareCount(content.getShareCount());
				UserInfo userInfo = userInfoMapper.get(content.getUserId());
				contentv.setIssuerFaceSrc(CommonUtils.getImage(userInfo.getFaceSrc()));
				contentv.setIssuerName(userInfo.getNickName());
				contentv.setCreateDate(CommonUtils.dateToUnixTimestamp(content.getCreateDate(), 
							CommonConstants.DATETIME_SEC));
				int cc = commentMapper.getTotalCount(CommonConstants.KJUNS_CONTENT_COMMENTS, content.getId(), null);
				contentv.setCommentCount(cc);
				if(CommonUtils.notEmpty(sectionId) && CommonUtils.notEmpty(userId)){
					int c  = sectionMapper.getRssCount(userId, sectionId);
					contentv.setIsRss(c > 0 ? 0 : 1);
				}else if(CommonUtils.notEmpty(sectionId) ){
					int c  = sectionMapper.getRssCount(null, sectionId);
					contentv.setRssCount(c);
				}
				contentList.add(contentv);
			}
		}
		pageList.setPageInvertedIndex(page.getReturnIndex());
		pageList.setTotalCount(count);
		pageList.setList(contentList);
		return pageList;
	}

	@Override
	public PageList queryCampContent(Page page) throws Exception {
		List<ContentVo> contentList = new ArrayList<>();
		int count = campMapper.getTotalCount();
		PageList pageList = new PageList();
		if(count > 0){
			page.setTotalCount(count);
			List<Content> contents = campMapper.queryCampList(page.getStart(), page.getPageSize());
			for(Content content:contents){
				ContentVo contentv = new ContentVo();
				contentv.setId(content.getId());
				contentv.setTitle(content.getTitle());
				contentv.setSummary(content.getSummary());
				contentv.setType(1);
				contentv.setThumbnail(CommonUtils.getImage(content.getThumbnail()));
				contentv.setLikeCount(content.getLikeCount());
				contentv.setShareCount(content.getShareCount());
				UserInfo userInfo = userInfoMapper.get(content.getIssuerId());
				System.out.println(userInfo);
				contentv.setIssuerFaceSrc(CommonUtils.getImage(userInfo.getFaceSrc()));
				contentv.setIssuerName(userInfo.getNickName());
				contentv.setCreateDate(CommonUtils.dateToUnixTimestamp(content.getCreateDate(), 
							CommonConstants.DATETIME_SEC));
				int cc = commentMapper.getTotalCount( CommonConstants.KJUNS_CAMP_COMMENTS, content.getId(), null);
				contentv.setCommentCount(cc);
				contentList.add(contentv);
			}
		}
		pageList.setPageInvertedIndex(page.getReturnIndex());
		pageList.setTotalCount(count);
		pageList.setList(contentList);
		return pageList;
	}

	@Override
	public ContentVo selectCampById(String id) throws Exception {
		ContentVo contentVo = campMapper.selectById(id);
		//标签
		List<ContentTag> tagList = contentTagMapper.queryContentTagForContentId(id);
		if(null != tagList){
			contentVo.setContentTagList(tagList);	
		}
		//相关推荐
		List<ContentRelatedArticles> relatedArticlesList = 
				contentRelatedArticlesMapper.queryContentRelatedArticlesForContentId(id);
		if(null != relatedArticlesList){
			contentVo.setContentRelatedArticlesList(relatedArticlesList);
		}
		//发布人
		if(CommonUtils.notEmpty(contentVo.getIssuerId())){
			UserInfo userInfo = userInfoMapper.get(contentVo.getIssuerId());
			contentVo.setIssuerId(contentVo.getIssuerId());
			contentVo.setIssuerName(userInfo.getNickName());
			contentVo.setIssuerFaceSrc(CommonUtils.getImage(userInfo.getFaceSrc()));
		}
		if(CommonUtils.notEmpty(contentVo.getDateTime())){
			contentVo.setCreateDate(CommonUtils.dateToUnixTimestamp(contentVo.getDateTime(), 
					CommonConstants.DATETIME_SEC));
		}
		if(CommonUtils.notEmpty(contentVo.getMindMap())){
			contentVo.setMindMap(CommonUtils.getImage(contentVo.getMindMap()));
		}
		if(CommonUtils.notEmpty(contentVo.getThumbnail())){
			contentVo.setThumbnail(CommonUtils.getImage(contentVo.getThumbnail()));
		}
		return contentVo;
	}
	
}
