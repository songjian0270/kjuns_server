package com.kjuns.service;

import com.kjuns.model.PageList;
import com.kjuns.model.UserInfo;
import com.kjuns.out.BaseOutJB;
import com.kjuns.util.pager.Page;
import com.kjuns.vo.UserInfoVo;

public interface UserInfoService {
	
	/**
	 * 获取用户[id]
	 * @param id
	 * @return
	 */
	UserInfoVo getUserInfoById(String id) throws Exception;
	
	/**
	 * 完善信息
	 * @param baseUserInfo
	 * @return
	 */
	BaseOutJB saveUserInfo(UserInfo userInfo) throws Exception;
	
	/**
	 * 昵称是否存在
	 * @param nickName
	 * @return
	 * @throws Exception
	 */
	boolean nickNameIsExisting(String nickName) throws Exception;
	
	/**
	 * 获取关注用户
	 * @param token
	 * @param page
	 * @return
	 * @throws Exception
	 */
	PageList queryFocusUser(Long userId, Long myId, Page page) throws Exception;
	
	/**
	 * 获取粉丝
	 * @param token
	 * @param page
	 * @return
	 * @throws Exception
	 */
	PageList queryFansUser(Long userId, Long myId, Page page) throws Exception;

	/**
	 * 修改用户信息
	 * @param userInfo
	 * @return
	 */
	BaseOutJB updateUserInfo(UserInfo userInfo) throws Exception;
	
	/**
	 * 获取用户[token]
	 * @return
	 * @throws Exception
	 */
	UserInfo getUserInfoForToken(String token) throws Exception;
	
}
 