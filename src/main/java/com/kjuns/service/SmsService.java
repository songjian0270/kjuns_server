package com.kjuns.service;

import com.kjuns.util.ErrorCode;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-8-14
 * @file SmsService.java
 * @package com.kjuns.service
 * @project kjuns
 * @version 2.0
 */
public interface SmsService {
	
	/**
	 * 短信验证码发送
	 * @param cellphoneNumber
	 * @param diallingCode
	 * @return
	 * @throws Exception
	 */
	ErrorCode generateCheckCode(String cellPhoneNumber, String diallingCode) throws Exception;
	
	/**
	 * 短信验证码验证
	 * @param cellPhoneNumber
	 * @param diallingCod
	 * @return
	 * @throws Exception
	 */
	ErrorCode verifyCheckCode(String cellPhoneNumber, String checkCode) throws Exception;

}
