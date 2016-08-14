package com.kjuns.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.kjuns.service.CommentService;
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

	@Autowired
	private CommentService commentService;
	
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
	public void sectionList(String id, String token, Page page, Model model) throws Exception {
		try {
			String userId = null;
			UserInfo userInfo = this.getUserInfoForToken(token);
			if(null != userInfo){
				userId = userInfo.getId();
			}
			PageList pageList = contentService.querySectionContent(id, userId, page);
			sendResponseContent(model, ErrorCode.SUCCESS, pageList);
		} catch (Exception ex) {
			logger.error("list >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	@RequestMapping(value = "camp/list", method = RequestMethod.GET)
	public void campList(Page page, Model model) throws Exception {
		try {
			PageList pageList = contentService.queryCampContent( page);
			sendResponseContent(model, ErrorCode.SUCCESS, pageList);
		} catch (Exception ex) {
			logger.error("list >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	@RequestMapping(value = "camp/detail", method = RequestMethod.GET)
	public void campDetail(String id, Model model) throws Exception {
		try {
			ContentVo content = contentService.selectCampById(id);
			content.setContent("");
			content.setPageUrl("content/camp/view.h5?id="+id);
			sendResponseContent(model, ErrorCode.SUCCESS, content);
		} catch (Exception ex) {
			logger.error("list >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	@RequestMapping(value = "img/list", method = RequestMethod.GET)
	public void imgList(Page page, String token, Model model) throws Exception {
		try {
			String typeId= "2427d4151e334c6594231de41990da4c";
			String userId = null;
			UserInfo userInfo = this.getUserInfoForToken(token);
			if(null != userInfo){
				userId = userInfo.getId();
			}
			PageList pageList = contentService.queryContent(typeId, userId, page);
			sendResponseContent(model, ErrorCode.SUCCESS, pageList);
		} catch (Exception ex) {
			logger.error("list >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(String typeId, String token, Page page, Model model) throws Exception {
		try {
			String userId = null;
			UserInfo userInfo = this.getUserInfoForToken(token);
			if(null != userInfo){
				userId = userInfo.getId();
			}
			PageList pageList = contentService.queryContent(typeId, userId, page);
			sendResponseContent(model, ErrorCode.SUCCESS, pageList);
		} catch (Exception ex) {
			logger.error("list >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void detail(String id, Model model) throws Exception {
		try {
			ContentVo content = contentService.selectById(id);
			content.setContent("");
			content.setPageUrl("content/view.h5?id="+id);
			sendResponseContent(model, ErrorCode.SUCCESS, content);
		} catch (Exception ex) {
			logger.error("list >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	@VerifyToken
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public void del(String id, HttpServletRequest request, String token, Model model) throws Exception {
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
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(Content content, HttpServletRequest request, String token, Model model) throws Exception {
		try {
			UserInfo userInfo = this.getUserInfoForToken(token);
			content.setUserId(userInfo.getId());
			BaseOutJB jb = contentService.insertCamp(content);
			sendResponseContent(model, jb);
		} catch (Exception ex) {
			logger.error("del >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	@VerifyToken
	@RequestMapping(value = "/like", method = RequestMethod.POST)
	public void like(String token, HttpServletRequest request, String id, Model model) throws Exception {
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
	public void share(String token, HttpServletRequest request, String id, Model model) throws Exception {
		try {
			ErrorCode errorCode = contentService.insertContentShare(id);
			sendResponseContent(model, errorCode);
		} catch (Exception ex) {
			logger.error("share >>> {}", ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}
	
	@IgnoreVerify
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(String id,String isFull, Model model) throws Exception {
		try {
			ContentVo content = contentService.selectById(id);
			Page page = new Page(0,2);
			PageList hotComments = commentService.queryContentComments(id, 0, "1",page );
			PageList newComments = commentService.queryContentComments(id, 0, "0",page );
			model.addAttribute("isFull",isFull);
			model.addAttribute("content",content);
			model.addAttribute("hotComments",hotComments);
			model.addAttribute("newComments",newComments);
			return "/content/view";
		}catch (Exception ex) {
			return "/content/error";
		}
	}
	

	@IgnoreVerify
	@RequestMapping(value = "camp/view", method = RequestMethod.GET)
	public String campView(String id,String isFull, Model model) throws Exception {
		try {
			ContentVo content = contentService.selectCampById(id);
			Page page = new Page(0,2);
			PageList hotComments = commentService.queryContentComments(id, 0, "1",page );
			PageList newComments = commentService.queryContentComments(id, 0, "0",page );
			model.addAttribute("isFull",isFull);
			model.addAttribute("isCamp","1");
			model.addAttribute("content",content);
			model.addAttribute("hotComments",hotComments);
			model.addAttribute("newComments",newComments);
			return "/content/view";
		}catch (Exception ex) {
			return "/content/error";
		}
	}
}
