package com.kjuns.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kjuns.annotation.VerifyToken;
import com.kjuns.model.PageList;
import com.kjuns.model.UserInfo;
import com.kjuns.out.BaseOutJB;
import com.kjuns.service.CommentService;
import com.kjuns.util.ErrorCode;
import com.kjuns.util.pager.Page;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-9-6
 * @file CommentController.java
 * @package com.kjuns.controller
 * @project kjuns
 * @version 2.0
 */
@Controller
@RequestMapping("/comments")
public class CommentController extends BaseController{
	
	@Autowired
	private CommentService commentService;
	
	/**
	 * 获取评论列表
	 * 
	 * @param dynamicId
	 * @param model
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Model queryComment(String id, Page page, HttpServletResponse response, 
			Model model) throws Exception {
		try {
			response.addHeader("Access-Control-Allow-Origin", "*");
			PageList list = commentService.queryContentComments(id, page);
			sendResponseContent(model, ErrorCode.SUCCESS, list);
			return model;
		} catch (Exception ex) {
			logger.error("comments >>> {}", ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * 评论
	 * @param userDynamicComments
	 * @param userWorkComments
	 * @param model
	 * @throws Exception
	 */
	@VerifyToken
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(String id, String replyCommentId, String content, String token, 
			HttpServletRequest request, Model model) throws Exception{
		try {
			UserInfo userInfo = this.getUserInfoForToken(token);
			BaseOutJB b = commentService.insertContentComments(id, replyCommentId, content, userInfo.getId());
			sendResponseContent(model, b);
		} catch (Exception ex) {
			logger.error("comment >>>> {}",ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * 删除评论
	 * @param id
	 * @param model
	 */
	@VerifyToken
	@RequestMapping(value = "/del", method = RequestMethod.DELETE)
	public void delComment(String token, String commentId, String contentId, Model model){
		try {
			UserInfo userInfo = this.getUserInfoForToken(token);
			boolean b  = commentService.delContentCommentById(contentId, commentId, userInfo.getId());
			if(b){
				sendResponseContent(model, ErrorCode.SUCCESS);
			}else{
				sendResponseContent(model, ErrorCode.FAILED);
			}
		} catch (Exception ex) {
			logger.error("delDynamicComment >>>> {}", ex.getMessage());
		}
	}

}
