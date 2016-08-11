package com.kjuns.vo;

public class ContentShareVo {
	
	private String nickName;
	
	private String faceSrc;
	
	private String userId;
	
	private Long createDate=0l;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFaceSrc() {
		return faceSrc;
	}

	public void setFaceSrc(String faceSrc) {
		this.faceSrc = faceSrc;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	
}
