package com.kjuns.model;

public class UserAccount extends BaseModel{
	
	private String userId;
	
	private String mobilePhone;
	
	private String qqUid;
	
	private String wbUid;
	
	private String wxUid;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getQqUid() {
		return qqUid;
	}

	public void setQqUid(String qqUid) {
		this.qqUid = qqUid;
	}

	public String getWbUid() {
		return wbUid;
	}

	public void setWbUid(String wbUid) {
		this.wbUid = wbUid;
	}

	public String getWxUid() {
		return wxUid;
	}

	public void setWxUid(String wxUid) {
		this.wxUid = wxUid;
	}
	
}
