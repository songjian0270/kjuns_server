package com.kjuns.service;

import com.kjuns.model.LoginInfo;
import com.kjuns.out.BaseOutJB;
import com.kjuns.util.ErrorCode;

public interface UserLoginService {
	
	/**
	 * 登录
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	BaseOutJB updateAccount(LoginInfo loginInfo) throws Exception;
	
	/**
	 * 完善信息
	 * @param cellPhoneNumber
	 * @param nickName
	 * @param faceSrc
	 * @param idCard
	 * @param realName
	 * @param model
	 * @return
	 * @throws Exception
	 */
	BaseOutJB complete(String cellPhoneNumber, String nickName, String idCard, String realName) throws Exception;
	
	/**
	 * 手机是否存在
	 * @param cellPhoneNumber
	 * @return
	 * @throws Exception
	 */
	boolean cellPhoneNumberIsExisting(String cellPhoneNumber) throws Exception;
	
	/**
	 * 绑定
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	BaseOutJB binding(LoginInfo loginInfo) throws Exception;
	
	/**
	 * 检测绑定
	 * @param token
	 * @return
	 * @throws Exception
	 */
	BaseOutJB bindingIsExisting(String userId) throws Exception;
	
	/**
	 * 退出
	 * @param token
	 * @return
	 * @throws Exception
	 */
	ErrorCode updateAccountForToken(String token) throws Exception;
	
}
