package com.kjuns.validation; 

public class Vali {
	
	private String value;
	
	private String msg;
	
	public Vali(String value, String msg){
		this.value = value;
		this.msg = msg;
	}
	
	/** 对比值  */
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	/** 校验信息  */
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
 