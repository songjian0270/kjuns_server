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
import com.kjuns.util.SysConf;
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

	private final Integer timeout = 60; // 超时时间

	private final Integer expire = 900; // 过期时间

	@Autowired
	private SMSMapper smsMapper;

	@Override
	public ErrorCode generateCheckCode(String cellPhoneNumber, String diallingCode) throws Exception {
		String phone = diallingCode + "-" + cellPhoneNumber;
		boolean isExit = true;
		SMS sms = smsMapper.getSMSForMobilePhone(phone);
		if (null != sms && CommonUtils.notEmpty(sms.getMistiming()) && sms.getMistiming() < timeout) {
			return ErrorCode.SMS_CODE_REPEAT;
		} else {
			String checkCode = CommonUtils.getSmsCode();
			if (null != sms && CommonUtils.notEmpty(sms.getCode()) && sms.getMistiming() < expire) {
				checkCode = sms.getCode();
				isExit = false;
			}
			String resultString = SmsMain.sendSms("webapp", cellPhoneNumber, "加入看军事,我们一起驰骋星辰大海:" + checkCode, 3, 1, "");
			if (resultString.equals("success")) {
				String datetime = CommonConstants.DATETIME_SEC.format(new Date());
				String id = UUIDUtils.getUUID().toString().replace("-", "");
				SMS smsEntity = new SMS();
				smsEntity.setCode(checkCode);
				smsEntity.setId(id);
				smsEntity.setMobilePhone(phone);
				smsEntity.setCreateDate(datetime);
				if(isExit){
					smsMapper.delSMSForMobilePhone(phone);
					smsMapper.insertSMS(smsEntity);	
				}
				return ErrorCode.SUCCESS;
			} else {
				return ErrorCode.SMS_SEND_FAILD;
			}
		}
	}

	/** 验证码验证 */
	public ErrorCode verifyCheckCode(String cellPhoneNumber, String checkCode) {
		if (CommonUtils.notEmpty(cellPhoneNumber) && CommonUtils.notEmpty(checkCode)) {
			SMS sms = smsMapper.getSMSForMobilePhone(cellPhoneNumber);
			logger.info("send phone:" + cellPhoneNumber);
			logger.info("sms verifyCheckCode:" + checkCode);
			if(cellPhoneNumber.equals(SysConf.ADMIN_MOBILE_PHONE) && checkCode.equals(SysConf.ADMIN_MOBILE_PASS)){
				return ErrorCode.SUCCESS;
			}
			if (CommonUtils.notEmpty(sms) && sms.getMistiming() < expire) {
				if (!checkCode.equals(sms.getCode())) {
					return ErrorCode.SMS_CODE_ERROR;
				} else {
					return ErrorCode.SUCCESS;
				}
			} else {
				return ErrorCode.SMS_CODE_TIMEOUT;
			}
		}
		return ErrorCode.SMS_CODE_TIMEOUT;
	}

}
