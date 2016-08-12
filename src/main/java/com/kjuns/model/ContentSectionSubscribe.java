package com.kjuns.model;

public class ContentSectionSubscribe extends BaseModel {
	public String userId;
	public String sectionId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
}
