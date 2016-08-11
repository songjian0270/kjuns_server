package com.kjuns.model;

public class ContentRelatedArticles extends BaseModel{
	
	private String relatedArticlesId;
	
	private String contentId;
	
	private String relatedArticlesName;

	public String getRelatedArticlesId() {
		return relatedArticlesId;
	}

	public void setRelatedArticlesId(String relatedArticlesId) {
		this.relatedArticlesId = relatedArticlesId;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getRelatedArticlesName() {
		return relatedArticlesName;
	}

	public void setRelatedArticlesName(String relatedArticlesName) {
		this.relatedArticlesName = relatedArticlesName;
	}
	
}
