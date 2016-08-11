package com.kjuns.handler;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.kjuns.controller.BaseController;
import com.kjuns.exception.AuthorizeException;
import com.kjuns.exception.RepeatSubmitException;
import com.kjuns.exception.SMSException;
import com.kjuns.exception.TokenInvalidException;
import com.kjuns.util.CommonUtils;

public class ExceptionHandler extends BaseController implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, 
			HttpServletResponse response, Object handler,
			Exception ex) {
		try {
			String msg = "";
			String type = "";
			if(CommonUtils.notEmpty(ex.getMessage())){
				msg = java.net.URLEncoder.encode(ex.getMessage(), "utf-8");
			}
			if(ex instanceof TokenInvalidException){
				type = "tokenInvalid";
				return new ModelAndView("redirect:/exception/reminder?msg=" + msg + "&type=" + type);
			}else if(ex instanceof AuthorizeException){
				type = "authorize";
				return new ModelAndView("redirect:/exception/reminder?msg=" + msg + "&type=" + type);
			}else if(ex instanceof SMSException){
				type = "sms";
				return new ModelAndView("redirect:/exception/reminder?msg=" + msg + "&type=" + type);
			}else if(ex instanceof RepeatSubmitException){
				type = "repeatSubmit";
				return new ModelAndView("redirect:/exception/reminder?msg=" + msg + "&type=" + type);
			}else{
				return new ModelAndView("redirect:/exception/reminder?msg=" + msg + "&type=");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("ExceptionHandler >>>> 异常信息转换错误!");
		}
		return null;
	}

}
