package com.kjuns.util.db;

import com.kjuns.util.DBUtils;


public class SmsLogger implements Runnable	{

	private String mobile;
	
	private Integer sender;
	
	private Integer type;
	
	private String content;
	
	private String remark;
	
	private String reqIp;
	
	private String createDate;

	public SmsLogger(){}
	
	public SmsLogger(String mobile, Integer sender, Integer type, String content, String remark,
			String reqIp, String createDate){
		this.mobile = mobile;
		this.sender = sender;
		this.type = type;
		this.content = content;
		this.remark = remark;
		this.reqIp = reqIp;
		this.createDate = createDate;
	}

	@Override
	public void run() {
		new DBUtils().smslogger(mobile, sender, type, content, remark, reqIp, createDate);
	}

}
