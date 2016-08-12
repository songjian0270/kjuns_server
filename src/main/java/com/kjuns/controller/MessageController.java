package com.kjuns.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kjuns.annotation.VerifyToken;
import com.kjuns.util.ErrorCode;
import com.kjuns.util.pager.Page;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-9-16
 * @file MessageController.java
 * @package com.kjuns.controller
 * @project kjuns
 * @version 2.0
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController{
	
	//@Autowired
	//private MessageService messageService;
	
	/**
	 * school
	 * @param model
	 */
	@VerifyToken
	@RequestMapping(value="/school", method= RequestMethod.GET)
	public void querySchool(String token, Page page, HttpServletRequest request, 
			Model model) throws Exception{
		try {
			
			sendResponseContent(model, ErrorCode.SUCCESS, null);
		} catch (Exception ex) {
			logger.error("querySchool  >>>> {}",ex.getMessage());
			throw ex;
		}
	}
	
}
