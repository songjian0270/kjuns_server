package com.kjuns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kjuns.util.ErrorCode;

/**
 * <b>Function: </b>  异常处理
 * @author James
 * @date 2015-8-31
 * @file MealController.java
 * @package com.kjuns.controller
 * @project kjuns
 * @version 2.0
 */
@Controller
@RequestMapping("/exception")
public class ExceptionController extends BaseController{
	
	@RequestMapping("/reminder")
	public void reminder(String msg, String type, Model model){
		switch (type) {
			case "tokenInvalid":
				sendResponseContent(model, ErrorCode.NEED_LOGIN, msg);
				break;
			case "authorize":
				sendResponseContent(model, ErrorCode.LOGIN_THIRD_PART_ERROR, msg);
				break;
			case "sms":
				sendResponseContent(model, ErrorCode.SMS_SEND_FAILD, msg);
				break;
			case "repeatSubmit":
				sendResponseContent(model, ErrorCode.REPEAT_SUBMIT_ERROR, msg);
				break;
			default:
				sendResponseContent(model, ErrorCode.SYS_ERROR, msg);
				break;
		}
	}

}
