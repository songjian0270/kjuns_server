package com.kjuns.controller; 

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.kjuns.model.UserInfo;
import com.kjuns.out.BaseOutJB;
import com.kjuns.service.UserInfoService;
import com.kjuns.util.ErrorCode;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-07-28
 * @file BaseController.java
 * @package com.kjuns.controller
 * @project kjuns
 * @version 2.0
 */
public class BaseController {

	public Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//@Autowired
	//private UserAccountService userAccountService;
	@Autowired
	private UserInfoService userInfoService;
	
	@SuppressWarnings("unchecked")
	protected Map<String,Object> getContent(HttpServletRequest request){
		return (Map<String, Object>) request.getAttribute("content");
	}
	
	/**
	 * 根据token获取用户信息
	 * @param token
	 * @return
	 * @throws Exception 
	 */
	protected UserInfo getUserInfoForToken(String token) throws Exception{
		return userInfoService.getUserInfoForToken(token);
	}
	
	protected void sendResponseContent(Model model, BaseOutJB out){
		model.asMap().clear();
		model.addAttribute(out);
	}
	
	protected void sendResponseContent(Model model, Object data){
		model.asMap().clear();
		model.addAttribute(data);
	}
	
	protected void sendResponseContent(Model model, ErrorCode error){
		model.asMap().clear();
		BaseOutJB out = new BaseOutJB(error);
		model.addAttribute(out);
	}
	
	protected void sendResponseContent(Model model, ErrorCode error, Object data){
		model.asMap().clear();
		BaseOutJB out = null;
		if(data instanceof String || data instanceof Long || data instanceof Boolean
				|| data instanceof Integer){
			Map<String, Object> params = new HashMap<>();
			params.put("message", data);
			out = new BaseOutJB(error, params);
		}else{
			out = new BaseOutJB(error, data);
		}
		model.addAttribute(out);
	}
	
}

 