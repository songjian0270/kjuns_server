package com.kjuns.service;

import java.util.List;

import com.kjuns.model.PageList;
import com.kjuns.model.Content;
import com.kjuns.model.ContentType;
import com.kjuns.out.BaseOutJB;
import com.kjuns.util.ErrorCode;
import com.kjuns.util.pager.Page;
import com.kjuns.vo.ContentVo;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-9-6
 * @file ContentService.java
 * @package com.kjuns.service
 * @project kjuns
 * @version 2.0
 */
public interface ContentService {
	
	/**
	 * 内容类型
	 * @return
	 * @throws Exception
	 */
	List<ContentType> queryContentType() throws Exception;
	
	/**
	 * 内容列表
	 * @param typeId
	 * @param page
	 * @return
	 * @throws Exception
	 */
	PageList queryContent(String typeId, Page page) throws Exception;
	
	/**
	 * 专栏列表
	 * @return
	 * @throws Exception
	 */
	PageList querySectionContent(String sectionId, Page page) throws Exception;
	
	/**
	 * 添加内容
	 * @param Content
	 * @return
	 * @throws Exception
	 */
	BaseOutJB insertContent(Content Content) throws Exception;
	
	/**
	 * 删除内容
	 * @param contentId
	 * @return
	 */
	BaseOutJB delContentById(String id, String userId) throws Exception;
	
	ContentVo selectById(String id) throws Exception;
	
	ErrorCode insertContentLike(String id) throws Exception;
	
	ErrorCode insertContentShare(String id) throws Exception;
	
}
