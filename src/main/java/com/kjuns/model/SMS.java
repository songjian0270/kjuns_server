package com.kjuns.model;
/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-8-14
 * @file SMS.java
 * @package com.kjuns.model
 * @project kjuns
 * @version 2.0
 */
public class SMS extends BaseModel{
	
	private String mobilePhone;
	
	private String code;
	
	private int mistiming;

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getMistiming() {
		return mistiming;
	}

	public void setMistiming(int mistiming) {
		this.mistiming = mistiming;
	}

}
