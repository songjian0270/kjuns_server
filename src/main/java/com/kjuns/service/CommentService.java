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
	 * @param contentType 0  普通内容 1 阵营
	 * @param type	普通内容类别
	 * @param page
	 * @return
	 * @throws Exception
	 */
	PageList queryContentComments(String id, int contentType, String type, Page page)  throws Exception;
	
	/**
	 * 保存评论
	 * @param id
	 * @param replyCommentId
	 * @param content
	 * @param userId
	 * @param contentType 0  普通内容 1 阵营
	 * @return
	 * @throws Exception
	 */
	BaseOutJB insertContentComments(String id, String replyCommentId, String content, String userId, int contentType) throws Exception;
	
	/**
	 * 删除动态评论
	 * @param id
	 * @return
	 */
	boolean delContentCommentById(String contentId, String id, String userId, int contentType);
	
}
