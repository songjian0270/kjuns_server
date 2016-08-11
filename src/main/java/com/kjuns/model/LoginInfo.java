package com.kjuns.model;
/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-8-14
 * @file LoginInfo.java
 * @package com.kjuns.model
 * @project kjuns
 * @version 2.0
 */
public class LoginInfo {
	
	private String userId;
	private String cellPhoneNumber;
	private String checkCode;
	private String accessTokenType; //0:QQ 1:WX 2:WB 3:rr 4:fb 5:twi
	private String accessToken; 
	private String openId;
	
	private String token;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}
	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getAccessTokenType() {
		return accessTokenType;
	}
	public void setAccessTokenType(String accessTokenType) {
		this.accessTokenType = accessTokenType;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
