package com.kjuns.model;


public class Content extends BaseModel{

	private String userId;
	
	private String sectionId;
	
	private String title;
	
	private String subhead;
	
	private String source;
	
	private String sourceUrl;
	
	private String summary;
	
	private String background;
	
	private String thumbnail; //缩略图
	
	private String mindMap;
	
	private String content;
	
	private String type;
	
	private int isTop;
	
	private int isHot;		//热帖
	
	private int isDepth;	//深度
	
	private int isTease;	//吐槽
	
	private String issuers;
	
	private int like;
	
	private int share;

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

	public String getSubhead() {
		return subhead;
	}

	public void setSubhead(String subhead) {
		this.subhead = subhead;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIsTop() {
		return isTop;
	}

	public void setIsTop(int isTop) {
		this.isTop = isTop;
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

	public String getIssuers() {
		return issuers;
	}

	public void setIssuers(String issuers) {
		this.issuers = issuers;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

}
