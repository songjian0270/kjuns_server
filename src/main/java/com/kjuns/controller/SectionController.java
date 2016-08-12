package com.kjuns.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kjuns.annotation.VerifyToken;
import com.kjuns.model.UserInfo;
import com.kjuns.service.SectionService;
import com.kjuns.util.ErrorCode;

/**
 * <b>Function: </b>  专栏
 * @author James
 * @date 2016-07-22
 * @file SectionController.java
 * @package com.kjuns.controller
 * @project kjuns
 * @version 2.0
 */
@Controller
@RequestMapping("/section")
public class SectionController extends BaseController {

	@Autowired
	private SectionService sectionService;

	@VerifyToken
	@RequestMapping(value = "/subscribe", method = RequestMethod.PUT)
	public void subscribe(String token, String id,HttpServletRequest request, Model model) throws Exception {
		try {
			UserInfo userInfo = this.getUserInfoForToken(token);
			sectionService.subscribe(userInfo.getId(), id);
			sendResponseContent(model, ErrorCode.SUCCESS, null);
		} catch (Exception ex) {
			logger.error("subscribe  >>>> {}",ex.getMessage());
			throw ex;
		}
	}
}
