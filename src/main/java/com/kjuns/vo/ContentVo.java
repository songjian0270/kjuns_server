package com.kjuns.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kjuns.model.ContentRelatedArticles;
import com.kjuns.model.ContentTag;

@JsonInclude(Include.NON_NULL)
public class ContentVo {
	
	private String id;
	
	private String sectionId;

	private String title;
	
	private String source;
	
	private String sourceUrl;
	
	private String summary;
	
	private String thumbnail;
	
	private String mindMap;
	
	private String subhead;
	
	private int type;
	
	private String content;
	
	private String nickName;
	
	private List<ContentRelatedArticles> contentRelatedArticlesList;
	
	private List<ContentTag> contentTagList;
	
	private int isHot;		//热帖
	
	private int isDepth;	//深度
	
	private int isTease;	//吐槽
	
	private String issuerId;
	
	private String issuerFaceSrc;
	
	private String issuerName;
	
	private Long createDate;
	
	private long likeCount;
	
	private long shareCount;
	
	private List<ContentVo> contentList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getMindMap() {
		return mindMap;
	}

	public void setMindMap(String mindMap) {
		this.mindMap = mindMap;
	}

	public String getSubhead() {
		return subhead;
	}

	public void setSubhead(String subhead) {
		this.subhead = subhead;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<ContentRelatedArticles> getContentRelatedArticlesList() {
		return contentRelatedArticlesList;
	}

	public void setContentRelatedArticlesList(
			List<ContentRelatedArticles> contentRelatedArticlesList) {
		this.contentRelatedArticlesList = contentRelatedArticlesList;
	}

	public List<ContentTag> getContentTagList() {
		return contentTagList;
	}

	public void setContentTagList(List<ContentTag> contentTagList) {
		this.contentTagList = contentTagList;
	}

	public int getIsHot() {
		return isHot;
	}

	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}

	public int getIsDepth() {
		return isDepth;
	}

	public void setIsDepth(int isDepth) {
		this.isDepth = isDepth;
	}

	public int getIsTease() {
		return isTease;
	}

	public void setIsTease(int isTease) {
		this.isTease = isTease;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getIssuerFaceSrc() {
		return issuerFaceSrc;
	}

	public void setIssuerFaceSrc(String issuerFaceSrc) {
		this.issuerFaceSrc = issuerFaceSrc;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}

	public long getShareCount() {
		return shareCount;
	}

	public void setShareCount(long shareCount) {
		this.shareCount = shareCount;
	}

	public List<ContentVo> getContentList() {
		return contentList;
	}

	public void setContentList(List<ContentVo> contentList) {
		this.contentList = contentList;
	}
	
}
