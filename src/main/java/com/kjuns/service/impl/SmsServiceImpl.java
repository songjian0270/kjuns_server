package com.kjuns.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kjuns.mapper.SMSMapper;
import com.kjuns.model.SMS;
import com.kjuns.service.SmsService;
import com.kjuns.util.CommonConstants;
import com.kjuns.util.CommonUtils;
import com.kjuns.util.ErrorCode;
import com.kjuns.util.UUIDUtils;
import com.kjuns.util.sms.SmsMain;

/**
 * <b>Function: </b>
 * 
 * @author James
 * @date 2015-8-14
 * @file SmsServiceImpl.java
 * @package com.kjuns.service.impl
 * @project kjuns
 * @version 2.0
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {

	public Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final Integer timeout = 30;  //超时时间
	
	private final Integer expire = 900;	 //过期时间
	
	@Autowired
	private SMSMapper smsMapper;

	@Override
	public ErrorCode generateCheckCode(String cellPhoneNumber, String diallingCode) throws Exception{
		SMS sms = smsMapper.getSMSForMobilePhone(cellPhoneNumber);
		if (CommonUtils.notEmpty(sms.getMistiming()) && sms.getMistiming() < timeout) {
			return ErrorCode.SMS_CODE_REPEAT;
		} else{
			String checkCode = CommonUtils.getSmsCode();
			boolean b = true;
			if(null != sms && CommonUtils.notEmpty(sms.getCode()) && sms.getMistiming() < expire){
				checkCode = sms.getCode();
			}
	//		if (diallingCode.equals("86")) {
				SmsMain.sendSms("webapp",  cellPhoneNumber, "加入看军事,我们一起驰骋星辰大海:" + checkCode , 3, 1, "");
				if(b){
					String datetime = CommonConstants.DATETIME_SEC.format(new Date());
					String id = UUIDUtils.getUUID().toString().replace("-", "");
					SMS smsEntity = new SMS();
					smsEntity.setCode(checkCode);
					smsEntity.setId(id);
					smsEntity.setCreateDate(datetime);
					smsMapper.insetSMS(sms);
					return ErrorCode.SUCCESS;
				}else{
					return ErrorCode.SMS_SEND_FAILD;
				}
//			}else {
//				/* 添加验证码到redis 国外 */
//				SmsMain.sendSms("webapp", cellPhoneNumber, "加入看军事,我们一起驰骋星辰大海:" + checkCode , 3, 1, "");
//				if(b){
//					String id = UUIDUtils.getUUID().toString().replace("-", "");
//					SMS smsEntity = new SMS();
//					smsEntity.setCode(checkCode);
//					smsEntity.setId(id);
//					smsEntity.setCreateDate(datetime);
//					smsMapper.insetSMS(sms);
//					return ErrorCode.SUCCESS;
//				}else{
//					return ErrorCode.SMS_SEND_FAILD;
//				}
//			}
		}
	}
	
	/** 验证码验证 */
	public ErrorCode verifyCheckCode(String cellPhoneNumber, String checkCode) {
		if (CommonUtils.notEmpty(cellPhoneNumber) && CommonUtils.notEmpty(checkCode)) {
			SMS sms = smsMapper.getSMSForMobilePhone(cellPhoneNumber);
			logger.info("send phone:" + cellPhoneNumber);
			logger.info("sms verifyCheckCode:" + checkCode);
			logger.info("redis verifyCheckCode:" + sms.getMistiming());
			if (CommonUtils.notEmpty(sms) && sms.getMistiming() < expire) {
				if(CommonUtils.notEmpty(sms) && sms.getMistiming() < timeout){
					if (!checkCode.equals(sms.getCode())) {
						return ErrorCode.SMS_CODE_ERROR;
					}else{
						return ErrorCode.SUCCESS;
					}
				}else{
					return ErrorCode.SMS_CODE_REPEAT;	
				}
			} else {
				return ErrorCode.SMS_CODE_TIMEOUT;	
			}
		}
		return ErrorCode.SMS_CODE_TIMEOUT;
	}


}
