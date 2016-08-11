package com.kjuns.out; 

import java.util.HashMap;
import java.util.Map;

import com.kjuns.util.ErrorCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("result")
public class BaseOutJB {
	
	private String code;
	
	private String desc;
	
	private Object data;
	
	public BaseOutJB(){}
	
	public BaseOutJB(ErrorCode errorCode){
		this.code = errorCode.getCode();
		this.desc = errorCode.getDesc();
		Map<String, Object> params = new HashMap<>();
		params.put("message", "no data");
		this.data = params;
	}
	
	public BaseOutJB(ErrorCode errorCode, Object data){
		this.code = errorCode.getCode();
		this.desc = errorCode.getDesc();
		if(data instanceof String || data instanceof Long || data instanceof Boolean
				|| data instanceof Integer){
			Map<String, Object> params = new HashMap<>();
			params.put("message", data);
			this.data = params;
		}else{
			this.data = data;
		}
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
 