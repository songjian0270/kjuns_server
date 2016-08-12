package com.kjuns.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public PageList queryContent(String typeId, String userId, Page page) throws Exception {
		List<ContentVo> contentList = new ArrayList<>();
		PageList pageList = new PageList();
		int count = contentMapper.getTotalCount(typeId, null);
		if(count > 0){
			page.setTotalCount(count);
			int number = page.getPageSize() / SysConf.INTERVAL_NUMBER;		
			List<Content> list =  contentMapper.queryContentList(typeId, null, page.getStart(), page.getPageSize() - number);
			if(CommonUtils.notListFEmpty(list)){
				int i = 0; int k = 0 ;
				for(Content content: list){
					if(i == SysConf.INTERVAL_NUMBER - 1){
						int pageNumber =  page.getStart()/page.getPageSize();
						List<ContentSection> sectionList = sectionMapper.queryContentSectionList( pageNumber * number , number);
						int j = 0;
						if(CommonUtils.notListFEmpty(sectionList)){
							for(ContentSection section: sectionList){
								if(k == j){
									ContentVo contents = new ContentVo();
									contents.setId(content.getId());
									contents.setTitle(content.getTitle());
									contents.setSummary(content.getSummary());
									contents.setThumbnail(CommonUtils.getImage(content.getThumbnail()));
									contents.setLikeCount(content.getLikeCount());
									contents.setShareCount(content.getShareCount());
									contents.setType(1);
									if(CommonUtils.notEmpty(section.getId()) && CommonUtils.notEmpty(userId)){
										int c  = sectionMapper.getRssCount(userId, section.getId());
										contents.setIsRss(c > 0 ? 0 : 1);
									}else if(CommonUtils.notEmpty(section.getId()) ){
										int c  = sectionMapper.getRssCount(null, section.getId());
										contents.setRssCount(c);
									}
									List<Content> sectionContentList = contentMapper.queryContentList(null, section.getId(), 0, 6);
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
								j++;
							}
						}
						j ++;
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
	public BaseOutJB insertContent(Content content) throws Exception {
		String datetime = CommonConstants.DATETIME_SEC.format(new Date());
		content.setCreateDate(datetime);
		String id = UUIDUtils.getUUID().toString().replace("-", "");
		content.setId(id);
		boolean isSucceed = contentMapper.insertContent(content) > 0 ? true: false;
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
		ContentVo ContentVo = contentMapper.selectById(id);
		//标签
		List<ContentTag> tagList = contentTagMapper.queryContentTagForContentId(id);
		ContentVo.setContentTagList(tagList);
		//相关推荐
		List<ContentRelatedArticles> relatedArticlesList = 
				contentRelatedArticlesMapper.queryContentRelatedArticlesForContentId(id);
		ContentVo.setContentRelatedArticlesList(relatedArticlesList);
		//发布人
		if(CommonUtils.notEmpty(ContentVo.getIssuerId())){
			UserInfo userInfo = userInfoMapper.get(ContentVo.getIssuerId());
			ContentVo.setIssuerId(ContentVo.getIssuerId());
			ContentVo.setIssuerName(userInfo.getNickName());
			ContentVo.setIssuerFaceSrc(CommonUtils.getImage(userInfo.getFaceSrc()));
		}
		
		if(CommonUtils.notEmpty(ContentVo.getMindMap())){
			ContentVo.setMindMap(CommonUtils.getImage(ContentVo.getMindMap()));
		}
		if(CommonUtils.notEmpty(ContentVo.getThumbnail())){
			ContentVo.setThumbnail(CommonUtils.getImage(ContentVo.getThumbnail()));
		}
		return ContentVo;
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
		List<ContentVo> contentList = new ArrayList<>();
		int count = contentMapper.getTotalCount(null, sectionId);
		PageList pageList = new PageList();
		if(count > 0){
			page.setTotalCount(count);
			List<Content> contents = contentMapper.queryContentList(null, sectionId, page.getStart(), page.getPageSize());
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
	
}
