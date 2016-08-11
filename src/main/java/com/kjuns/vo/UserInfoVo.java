package com.kjuns.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-8-16
 * @file UserInfoVo.java
 * @package com.idol.vo
 * @project idol
 * @version 2.0
 */
@JsonInclude(Include.NON_NULL)
public class UserInfoVo {

	private String id;
	
	private String faceSrc = "";
	
	private String nickName = "";
	
	private Integer sex;
	
	private String realName;
	
	private String token;
	
	private String location ;

	private int isFocus = 0;
	
	private String createDate;
	
	private int joinYear;
	
	private int fansCount=0;
	
	private int likeCount=0;
	
	private int readingQuantity=0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFaceSrc() {
		return faceSrc;
	}

	public void setFaceSrc(String faceSrc) {
		this.faceSrc = faceSrc;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getIsFocus() {
		return isFocus;
	}

	public void setIsFocus(int isFocus) {
		this.isFocus = isFocus;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getFansCount() {
		return fansCount;
	}

	public void setFansCount(int fansCount) {
		this.fansCount = fansCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getReadingQuantity() {
		return readingQuantity;
	}

	public void setReadingQuantity(int readingQuantity) {
		this.readingQuantity = readingQuantity;
	}

	public int getJoinYear() {
		return joinYear;
	}

	public void setJoinYear(int joinYear) {
		this.joinYear = joinYear;
	}

}
