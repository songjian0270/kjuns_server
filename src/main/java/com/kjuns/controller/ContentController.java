package com.kjuns.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kjuns.annotation.IgnoreVerify;
import com.kjuns.annotation.VerifyToken;
import com.kjuns.model.Content;
import com.kjuns.model.ContentType;
import com.kjuns.model.PageList;
import com.kjuns.model.UserInfo;
import com.kjuns.out.BaseOutJB;
import com.kjuns.service.ContentService;
import com.kjuns.util.ErrorCode;
import com.kjuns.util.pager.Page;
import com.kjuns.vo.ContentVo;

/**
 * <b>Function: </b> 内容  
 * @author James
 * @date 2015-8-14
 * @file ContentController.java
 * @package com.kjuns.controller
 * @project kjuns
 * @version 2.0
 */
@Controller
@RequestMapping("/content")
public class ContentController extends BaseController{
	
	@Autowired 
	private ContentService contentService;
	
	@IgnoreVerify
	@RequestMapping(value = "/type/list", method = RequestMethod.GET)
	public void typeList(Model model) throws Exception {
		try {
			List<ContentType> list= contentService.queryContentType();
			sendResponseContent(model, ErrorCode.SUCCESS, list);
		} catch (Exception ex) {
			logger.error("typeList >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	@RequestMapping(value = "section/list", method = RequestMethod.GET)
	public void sectionList(String id, Page page, Model model) throws Exception {
		try {
			PageList pageList = contentService.querySectionContent(id, page);
			sendResponseContent(model, ErrorCode.SUCCESS, pageList);
		} catch (Exception ex) {
			logger.error("list >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(String typeId, Page page, Model model) throws Exception {
		try {
			PageList pageList = contentService.queryContent(typeId, page);
			sendResponseContent(model, ErrorCode.SUCCESS, pageList);
		} catch (Exception ex) {
			logger.error("list >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void detail(String id, Model model) throws Exception {
		try {
			ContentVo Content = contentService.selectById(id);
			sendResponseContent(model, ErrorCode.SUCCESS, Content);
		} catch (Exception ex) {
			logger.error("list >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	@VerifyToken
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public void del(String id, String token, Model model) throws Exception {
		try {
			UserInfo userInfo = this.getUserInfoForToken(token);
			BaseOutJB jb = contentService.delContentById(id, userInfo.getId());
			sendResponseContent(model, jb);
		} catch (Exception ex) {
			logger.error("del >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	@VerifyToken
	@RequestMapping(value = "/add", method = RequestMethod.PUT)
	public void add(Content content, String token, Model model) throws Exception {
		try {
			UserInfo userInfo = this.getUserInfoForToken(token);
			content.setUserId(userInfo.getId());
			BaseOutJB jb = contentService.insertContent(content);
			sendResponseContent(model, jb);
		} catch (Exception ex) {
			logger.error("del >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	@VerifyToken
	@RequestMapping(value = "/like", method = RequestMethod.POST)
	public void like(String token, String id, Model model) throws Exception {
		try {
			ErrorCode errorCode = contentService.insertContentLike(id);
			sendResponseContent(model, errorCode);
		} catch (Exception ex) {
			logger.error("like >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	@VerifyToken
	@RequestMapping(value = "/share", method = RequestMethod.POST)
	public void share(String token, String id, Model model) throws Exception {
		try {
			ErrorCode errorCode = contentService.insertContentShare(id);
			sendResponseContent(model, errorCode);
		} catch (Exception ex) {
			logger.error("share >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
}
