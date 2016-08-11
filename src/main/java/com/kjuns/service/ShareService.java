package com.kjuns.service;

import com.kjuns.model.PageList;
import com.kjuns.util.pager.Page;

public interface ShareService {
	
	/**
	 * 保存分享信息
	 * @param id
	 * @param channel
	 * @param url
	 * @return
	 * @throws Exception
	 */
	boolean insertUserShare(String id, String channel, String url, String userId) throws Exception; 
	
	/**
	 * 获取分享动态用户列表
	 * @param id
	 * @param page
	 * @return
	 */
	PageList queryShareUser(String id, Page page) throws Exception;
	
}
