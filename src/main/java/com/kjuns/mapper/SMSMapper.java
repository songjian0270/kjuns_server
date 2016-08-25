package com.kjuns.mapper;

import org.apache.ibatis.annotations.Param;

import com.kjuns.model.SMS;

public interface SMSMapper {
	
	/**
	 * 删除验证码
	 * @param id
	 * @return
	 */
	int delSMSForMobilePhone(@Param("mobilePhone")String mobilePhone);

	/**
	 * 插入验证码
	 * @param comment
	 * @return
	 */
	int insertSMS(SMS sms);
	
	/**
	 * 获取验证码
	 * @return
	 */
	SMS getSMSForMobilePhone(@Param("mobilePhone")String mobilePhone); 
	
}
