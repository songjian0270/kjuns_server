package com.kjuns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kjuns.exception.SMSException;
import com.kjuns.service.SmsService;
import com.kjuns.util.ErrorCode;

/**
 * <b>Function: </b> 短信   
 * @author James
 * @date 2015-8-14
 * @file BaseFeatureController.java
 * @package com.kjuns.controller
 * @project kjuns
 * @version 2.0
 */
@Controller
@RequestMapping("/sms")
public class SmsController extends BaseController{
	
	@Autowired 
	private SmsService smsService;
	
	/**
	 * 短信验证码发送
	 * @param cellphoneNumber
	 * @param diallingCode
	 * @param model
	 */
	@RequestMapping(value = "/generateCheckCode", method = RequestMethod.POST)
	public void generateCheckCode(String cellPhoneNumber, String diallingCode, Model model) throws Exception {
		try {
			ErrorCode error = smsService.generateCheckCode(cellPhoneNumber, diallingCode);
			sendResponseContent(model, error);
		} catch (Exception ex) {
			logger.error("generateCheckCode >>> {}", ex.getMessage());
			throw new SMSException(ex.getMessage());
		}
	}
	
	/**
	 * 验证码
	 * @param loginInfo
	 * @param model
	 * @throws Exception 
	 */
	@RequestMapping(value = "/verifyCheckCode", method = RequestMethod.POST)
	public void verifyCheckCode(String cellPhoneNumber, String checkCode, Model model) throws Exception {
		try{
			ErrorCode rc = smsService.verifyCheckCode(cellPhoneNumber, checkCode);
			sendResponseContent(model, rc);
		} catch (Exception ex) {
			logger.error("verifyCheckCode >>> {}", ex.getMessage());
			throw ex;
		}
	}
	
}
