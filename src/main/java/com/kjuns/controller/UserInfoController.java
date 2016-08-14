package com.kjuns.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kjuns.annotation.VerifyToken;
import com.kjuns.model.UserInfo;
import com.kjuns.out.BaseOutJB;
import com.kjuns.service.UserInfoService;
import com.kjuns.util.ErrorCode;
import com.kjuns.vo.UserInfoVo;

/**
 * <b>Function: </b> 用户基础信息  
 * @author James
 * @date 2015-8-24
 * @file UserDynamicController.java
 * @package com.kjuns.controller
 * @project kjuns
 * @version 2.0
 */
@Controller
@RequestMapping("/user/info")
public class UserInfoController extends BaseController {

	@Autowired
	private UserInfoService userInfoService;

//	/**
//	 * 完善信息
//	 * @param baseUserInfo 头像，昵称，性别，token
//	 * @param model
//	 */
//	@VerifyToken
//	@RequestMapping(value = "/completeUserInfo", method = RequestMethod.POST)
//	public void completeUserInfo(UserInfo userInfo, HttpServletRequest request,
//			Model model)  throws Exception {
//		try {
//			UserInfo ui = this.getUserInfoForToken(userInfo.getToken());
//			userInfo.setId(ui.getId());
//			BaseOutJB out = userInfoService.saveUserInfo(userInfo);
//			sendResponseContent(model, out);
//		} catch (Exception ex) {
//			logger.error("completeUserInfo >>> {}", ex.getMessage());
//			throw ex;
//		}	
//	}
	
	/**
	 * 获取用户信息
	 * @param id	
	 * @param model
	 */
	@VerifyToken
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void getUserInfo(String token, HttpServletRequest request, 
			Model model)  throws Exception {
		logger.info("UserInfoController getUserInfoById start.....");
		try {
			UserInfo ui = this.getUserInfoForToken(token);
			UserInfoVo userInfo = userInfoService.getUserInfoById(ui.getId());
			if(userInfo == null){
				sendResponseContent(model, ErrorCode.USER_INFO_NOT_EXIST);
			}else{
				sendResponseContent(model, ErrorCode.SUCCESS, userInfo);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("getUserInfoById >>> {}", ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * 获取他人信息
	 * @param id	
	 * @param model
	 */
	@RequestMapping(value = "/other/detail", method = RequestMethod.GET)
	public void getOtherUserInfo(String userId, HttpServletRequest request, 
			String token, Model model)  throws Exception {
		logger.info("UserInfoController getUserInfoById start.....");
		try {
			UserInfoVo userInfo = userInfoService.getUserInfoById(userId);
			if(userInfo == null){
				sendResponseContent(model, ErrorCode.USER_INFO_NOT_EXIST);
			}else{
				sendResponseContent(model, ErrorCode.SUCCESS, userInfo);
			}
		} catch (Exception ex) {
			logger.error("getOtherUserInfo >>> {}", ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * 修改用户信息
	 * @param baseUserInfo
	 * @param model
	 */
	@VerifyToken
	@RequestMapping(value = "/detail", method = RequestMethod.PUT)
	public void changeUserInfo(UserInfo userInfo, 
			HttpServletRequest request, Model model) throws Exception {
		try{
			UserInfo ui = this.getUserInfoForToken(userInfo.getToken());
			userInfo.setId(ui.getId());
			BaseOutJB outjb = userInfoService.updateUserInfo(userInfo);
			sendResponseContent(model, outjb);
		}catch(Exception ex){
			logger.error("changeUserInfo >>> {}", ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * 昵称是否存在
	 * @param nickName true存在
	 */
	@RequestMapping(value = "/nickNameIsExisting", method = RequestMethod.GET)
	public void nickNameIsExisting(String nickName, Model model)  throws Exception {
		try {
			boolean b = userInfoService.nickNameIsExisting(nickName);
			if(!b){
				sendResponseContent(model, ErrorCode.SUCCESS, b);
			}else{
				sendResponseContent(model, ErrorCode.NICK_NAME_EXIST_ERROR, b);
			}
		} catch (Exception ex) {
			logger.error("nickNameIsExisting >>> {}", ex.getMessage());
			throw ex;
		}
	}

}
