package com.kjuns.service;

import com.kjuns.model.PageList;
import com.kjuns.out.BaseOutJB;
import com.kjuns.util.pager.Page;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-9-6
 * @file CenterService.java
 * @package com.kjuns.service
 * @project kjuns
 * @version 2.0
 */
public interface CommentService {
	
	/**
	 * 用户评论列表
	 * @param id
	 * @param page
	 * @return
	 */
	PageList queryContentComments(String id, Page page)  throws Exception;
	
	/**
	 * 保存评论
	 * @param id
	 * @param replyCommentId
	 * @param content
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	BaseOutJB insertContentComments(String id, String replyCommentId, String content, String userId) throws Exception;
	
	/**
	 * 删除动态评论
	 * @param id
	 * @return
	 */
	boolean delContentCommentById(String contentId, String id, String userId);
	
}
