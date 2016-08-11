package com.kjuns.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserInfo extends BaseModel{
	
	private String realName="";
	
	private String nickName="";
	
	private String faceSrc="";
	
	private Integer sex;
	
	private String mobilePhone;
	
	private String location="";

	private long fansCount;
	
	private String idCard;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getFansCount() {
		return fansCount;
	}

	public void setFansCount(long fansCount) {
		this.fansCount = fansCount;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
}
