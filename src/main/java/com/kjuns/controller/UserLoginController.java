package com.kjuns.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kjuns.annotation.VerifyToken;
import com.kjuns.model.LoginInfo;
import com.kjuns.out.BaseOutJB;
import com.kjuns.service.UserLoginService;
import com.kjuns.util.ErrorCode;

/**
 * <b>Function: </b>  用户登陆  
 * @author James
 * @date 2015-8-24
 * @file UserLoginController.java
 * @package com.kjuns.controller
 * @project kjuns
 * @version 2.0
 */
@Controller
@RequestMapping("/user")
public class UserLoginController extends BaseController {
	
	@Autowired
	private UserLoginService userLoginService;
		
	/**
	 * 登陆
	 * @param loginInfo
	 * @param model
	 * @throws Exception 
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(LoginInfo loginInfo, Model model) throws Exception {
		try{
			BaseOutJB out = userLoginService.updateAccount(loginInfo);
			sendResponseContent(model, out);
		} catch (Exception ex) {
			logger.error("login >>> {}", ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * 完善信息
	 * @param cellPhoneNumber
	 * @param model
	 */
	@RequestMapping(value = "/complete", method = RequestMethod.POST)
	public void complete(String id, String nickName , String idcard, Model model) throws Exception{
		try {
			BaseOutJB out = userLoginService.complete(id, nickName, idcard);
			sendResponseContent(model, out);
		} catch (Exception ex) {
			logger.error("isExistCellPhoneNumber >>> {}", ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * 电话是否存在
	 * @param cellPhoneNumber
	 * @param model
	 */
	@RequestMapping(value = "/cellPhoneNumberIsExisting", method = RequestMethod.GET)
	public void cellPhoneNumberIsExisting(String cellPhoneNumber, Model model) throws Exception{
		try {
			boolean b = userLoginService.cellPhoneNumberIsExisting(cellPhoneNumber);
			if(b){
				sendResponseContent(model, ErrorCode.SUCCESS, b);
			}else{
				sendResponseContent(model, ErrorCode.EXIST, b);
			}
		} catch (Exception ex) {
			logger.error("isExistCellPhoneNumber >>> {}", ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * 退出
	 * @param token
	 * @throws Exception
	 */
	@VerifyToken
	@RequestMapping(value = "/loginOut", method = RequestMethod.POST)
	public void loginOut(String token, Model model, HttpServletRequest request)throws Exception {
		try {
			ErrorCode errorCode = userLoginService.updateAccountForToken(token);
			sendResponseContent(model, errorCode);
		} catch (Exception ex) {
			logger.error("loginOut >>>> {}", ex.getMessage());
			throw ex;
		}
	}
}
