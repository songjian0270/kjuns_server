package com.kjuns.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kjuns.annotation.IgnoreVerify;
import com.kjuns.annotation.VerifyToken;
import com.kjuns.model.UserFaq;
import com.kjuns.model.UserInfo;
import com.kjuns.service.CommonService;
import com.kjuns.util.ErrorCode;
import com.kjuns.vo.BannerVo;

/**
 * <b>Function: </b> 公用    
 * @author James
 * @updater Amadeus
 * @date 2015-8-25
 * @file CommonController.java
 * @package com.kjuns.controller
 * @project kjuns
 * @version 2.0
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController{
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 获取banner
	 */
	@IgnoreVerify
	@RequestMapping(value = "/banner/list", method = RequestMethod.GET)
	public Model queryBanner(HttpServletResponse response, Model model) throws Exception {
		try {
			//response.addHeader("Access-Control-Allow-Origin", ∂∂"*");
			List<BannerVo> list = commonService.queryBanner();
 			sendResponseContent(model, ErrorCode.SUCCESS, list);
 			return model;
		} catch (Exception ex) {
			logger.error("queryBanner >>> {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * 举报
	 * @param model
	 * @throws Exception
	 */
	@VerifyToken
	@RequestMapping(value = "/report", method = RequestMethod.POST)
	public void report(String token, String id, int type, HttpServletRequest request, Model model) throws Exception{
		try {
			UserInfo userInfo = getUserInfoForToken(token);
			ErrorCode errorCode = commonService.insertReport(userInfo.getId(), id, type);
			sendResponseContent(model, errorCode);
		} catch (Exception ex) {
			logger.error("report >>>> {}",ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * faq
	 * @param userFaq
	 * @param model
	 */
	@VerifyToken
	@RequestMapping(value = "/faq", method = RequestMethod.POST)
	public void insertFaq(UserFaq userFaq, HttpServletRequest request, Model model) throws Exception{
		try {
			UserInfo userInfo = getUserInfoForToken(userFaq.getToken());
			userFaq.setUserId(userInfo.getId());
			boolean b = commonService.saveFaq(userFaq);
			if(b){
				sendResponseContent(model, ErrorCode.SUCCESS);
			}else{
				sendResponseContent(model, ErrorCode.FAILED);
			}
		} catch (Exception ex) {
			logger.error("saveFaq >>>> {} ", ex.getMessage());
			throw ex;
		}
	}
	
}
