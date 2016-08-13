package com.kjuns.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kjuns.mapper.UserInfoMapper;
import com.kjuns.model.PageList;
import com.kjuns.model.UserInfo;
import com.kjuns.out.BaseOutJB;
import com.kjuns.service.UserInfoService;
import com.kjuns.util.CommonConstants;
import com.kjuns.util.CommonUtils;
import com.kjuns.util.ErrorCode;
import com.kjuns.util.pager.Page;
import com.kjuns.vo.UserInfoVo;

/**
 * <b>Function: </b>
 * 
 * @author James
 * @date 2015-07-28
 * @file RestServiceImpl.java
 * @package com.kjuns.service.impl
 * @project kjuns
 * @version 2.0
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	public Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public UserInfoVo getUserInfoById(String id) throws Exception {
		UserInfoVo userInfo = new UserInfoVo();
		if (CommonUtils.notEmpty(id)) {
			UserInfo ui = userInfoMapper.get(id);
			userInfo.setAutomaticThought(ui.getAutomaticThoughts());
			userInfo.setFaceSrc(CommonUtils.getImage(ui.getFaceSrc()));
			userInfo.setLocation(ui.getLocation());
			userInfo.setSex(ui.getSex());
			userInfo.setNickName(ui.getNickName());
		}
		return userInfo;
	}

	/** 昵称是否存在 */
	public boolean nickNameIsExisting(String nickName) throws Exception {
		boolean ishas = userInfoMapper.getForNickNameCount(nickName) > 0 ? false: true;
		return ishas;
	}

	/**
	 * 更新用户信息
	 * 
	 * @throws Exception
	 */
	public BaseOutJB updateUserInfo(UserInfo userInfo) throws Exception {
		boolean isUpdate = userInfoMapper.updateUserInfoById(userInfo) > 0 ? true: false;
		if (isUpdate) {
			return new BaseOutJB(ErrorCode.SUCCESS, true);
		} else {
			return new BaseOutJB(ErrorCode.FAILED, false);
		}
	}

	/** 添加用户信息 */
	public BaseOutJB saveUserInfo(UserInfo info) throws Exception {
		UserInfoVo v = new UserInfoVo();
		info.setCreateDate(CommonConstants.DATETIME_SEC.format(new Date()));
		info.setDataFlag("1");
		boolean ishas = true;
		if (ishas) {
			userInfoMapper.updateUserInfoById(info);
			v.setFaceSrc(info.getFaceSrc());
			v.setNickName(info.getNickName());
			v.setSex(info.getSex());
			v.setToken(info.getToken());
			v.setId(info.getId());
			return new BaseOutJB(ErrorCode.SUCCESS, v);
		} else {
			if (this.isRepetition(info.getNickName(), null)) {
				return new BaseOutJB(ErrorCode.NICK_NAME_EXIST_ERROR, null);
			}
			int result = userInfoMapper.insert(info);
			if (result > 0) {
				Map<String, Object> params = new HashMap<>();
				params.put("userId", info.getId().toString());
				params.put("token", info.getToken());
				//baseUserAccountMapper.updateBaseUserAccountByToken(params);
				v.setFaceSrc(info.getFaceSrc());
				v.setNickName(info.getNickName());
				v.setSex(info.getSex());
				v.setToken(info.getToken());
				v.setId(info.getId());
			}
			return new BaseOutJB(ErrorCode.SUCCESS, v);
		}
	}

	/**
	 * 判断昵称是否重复
	 * 
	 * @param nickName
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	protected boolean isRepetition(String nickName, Long userId)
			throws Exception {
		if (CommonUtils.notLongFEmpty(userId)) {
			String nickName_  = "";
			if (nickName_.equals(nickName)) {
				return false;
			} else {
				return this.nickNameIsExisting(nickName);
			}
		} else {
			return this.nickNameIsExisting(nickName);
		}
	}

	@Override
	public PageList queryFocusUser(Long userId, Long myId, Page page)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageList queryFansUser(Long userId, Long myId, Page page)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserInfo getUserInfoForToken(String token) throws Exception {
		return userInfoMapper.getForToken(token);
	}

}
