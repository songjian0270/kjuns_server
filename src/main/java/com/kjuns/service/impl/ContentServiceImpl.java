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
	public PageList queryContent(String typeId, Page page) throws Exception {
		List<ContentVo> ContentList = new ArrayList<>();
		PageList pageList = new PageList();
		int count = contentMapper.getTotalCount(typeId);
		if(count > 0){
			page.setTotalCount(count);
			int number = page.getPageSize() / SysConf.INTERVAL_NUMBER;		
			List<Content> list =  contentMapper.queryContentList(typeId, page.getStart(), page.getPageSize() - number);
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
									UserInfo userInfo = userInfoMapper.get(section.getIssuerId());
									contents.setIssuerFaceSrc(userInfo.getFaceSrc());
									contents.setIssuerName(userInfo.getNickName());
									contents.setCreateDate(CommonUtils.dateToUnixTimestamp(section.getCreateDate(), 
												CommonConstants.DATETIME_SEC));
								}
								j++;
							}
						}
						j ++;
						i = 0;
					}else{
						ContentVo contents = new ContentVo();
						UserInfo userInfo = userInfoMapper.get(content.getUserId());
						contents.setIssuerFaceSrc(userInfo.getFaceSrc());
						contents.setIssuerName(userInfo.getNickName());
						contents.setCreateDate(CommonUtils.dateToUnixTimestamp(content.getCreateDate(), 
									CommonConstants.DATETIME_SEC));
						ContentList.add(contents);
					}
					i++;
				}
			}
		}
		pageList.setPageInvertedIndex(page.getReturnIndex());
		pageList.setTotalCount(count);
		pageList.setList(ContentList);
		return pageList;
	}

	@Override
	public BaseOutJB insertContent(Content content) throws Exception {
		String datetime = CommonConstants.DATETIME_SEC.format(new Date());
		content.setCreateDate(datetime);
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
	
}
