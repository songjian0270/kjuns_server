package com.kjuns.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kjuns.exception.AuthorizeException;
import com.kjuns.mapper.UserAccountMapper;
import com.kjuns.mapper.UserInfoMapper;
import com.kjuns.model.LoginInfo;
import com.kjuns.model.UserAccount;
import com.kjuns.model.UserInfo;
import com.kjuns.out.BaseOutJB;
import com.kjuns.service.SmsService;
import com.kjuns.service.UserLoginService;
import com.kjuns.util.CommonConstants;
import com.kjuns.util.CommonUtils;
import com.kjuns.util.ErrorCode;
import com.kjuns.util.UUIDUtils;
import com.kjuns.util.WeiXinUtils;
import com.kjuns.util.weibo4j.Account;
import com.kjuns.util.weibo4j.Users;
import com.kjuns.util.weibo4j.model.User;
import com.kjuns.vo.UserInfoVo;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.utils.json.JSONObject;

/**
 * <b>Function: </b>
 * 
 * @author James
 * @date 2015-08-14
 * @file UserLoginServiceImpl.java
 * @package com.kjuns.service.impl
 * @project kjuns
 * @version 2.0
 */
@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {
	
	public Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserAccountMapper userAccountMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private SmsService smsService;
	
	public static final String QQ_TYPE_TOKEN = "0";
	public static final String WX_TYPE_TOKEN = "1";
	public static final String WB_TYPE_TOKEN = "2";

	/** 查询手机是否存在 */
	public boolean cellPhoneNumberIsExisting(String cellPhoneNumber) throws Exception {
		// 输入手机号
		if (CommonUtils.notEmpty(cellPhoneNumber)) {
			UserAccount ua = new UserAccount();
			ua.setMobilePhone(cellPhoneNumber);
			UserAccount userAccount = userAccountMapper.get(ua);
			if (CommonUtils.notEmpty(userAccount)) {
				return false;
			}
		}
		return true;
	}

	/** 登陆 */
	public BaseOutJB updateAccount(LoginInfo loginInfo) throws Exception {
		// 输入验证码 【手机登陆】
		if(CommonUtils.notEmpty(loginInfo.getCellPhoneNumber()) &&
				CommonUtils.notEmpty(loginInfo.getCheckCode())){
			ErrorCode errorCode = smsService.verifyCheckCode(loginInfo.getCellPhoneNumber(), loginInfo.getCheckCode());
			if(null != errorCode){
				if(errorCode.getCode().equals(ErrorCode.SUCCESS.getCode())){
					//判断手机登陆用户,存在返回用户信息。不存在则创建用户信息
					return this.updateForMobilePhone(loginInfo.getCellPhoneNumber());
				}else{
					return new BaseOutJB(errorCode);
				}
			}
		}
		// 第三方登陆授权
		else if (CommonUtils.notEmpty(loginInfo.getAccessTokenType()) && 
				CommonUtils.notEmpty(loginInfo.getAccessToken())) {
			return this.saveAccredit(loginInfo, false);
		}
		return new BaseOutJB(ErrorCode.LOGIN_ERROR);
	}
	
	/**
	 * 手机登陆处理
	 * @param cellPhoneNumber
	 * @return
	 */
	private BaseOutJB updateForMobilePhone(String mobilePhone) throws Exception{
		String datetime = CommonConstants.DATETIME_SEC.format(new Date());
		boolean isExist = this.cellPhoneNumberIsExisting(mobilePhone);
		 // 查询电话号码是否存在
		logger.info("updateForMobilePhone >> userAccount >> " + isExist );
		if(isExist){
			UserAccount ua = new UserAccount();
			ua.setMobilePhone(mobilePhone);
			ua = userAccountMapper.get(ua);
			UserInfo userInfo = userInfoMapper.get(ua.getUserId());
			 // 查询该手机号码是否存在基本信息
			logger.info("updateForMobilePhone >> userInfo >> " + userInfo==null?"false":"true" );
			if(CommonUtils.notEmpty(userInfo)){
				//判断基本信息是否完善 < 2.1 版本必填,之后不用判断>
				return this.updateUserAccountByUserId(ua.getId(), userInfo);
			}
		}else{
			UserAccount record = new UserAccount();
			record.setMobilePhone(mobilePhone);
			//MYSQL 查询是否存在
			record = userAccountMapper.get(record);
			if(null == record){
				record = new UserAccount();
				record.setMobilePhone(mobilePhone);
				//创建默认创建 userInfo   // < 2.1 版本还是需要完善信息>
//				UserInfo baseUserInfo = new UserInfo();
//				baseUserInfo.setFaceSrc("default_head.png");
//				int nickName = (int)(Math.random()*100000);
//				baseUserInfo.setMobilePhone(mobilePhone);
//				baseUserInfo.setNickName(nickName+"");
//				baseUserInfo.setSex(0);  //默认男
//				baseUserInfo.setCreateDate(datetime);
//				baseUserInfo.setDataFlag("1");
				int result = 0; //userInfoMapper.insert(baseUserInfo);
//				if( result > 0 ){
					//该电话号码无数据，则创建account
					String token = UUIDUtils.getUUID().toString().replace("-", "");
					record.setToken(token);
					record.setDataFlag("1");
				//	record.setUserId(baseUserInfo.getId());
					record.setCreateDate(datetime);
					record.setUpdateDate(datetime);
					result = userAccountMapper.insert(record);
					if( result > 0 ){
						return new BaseOutJB(ErrorCode.NOT_PERFECT_USER_INFO);
					}
				//}
			}else{
				//返回存在信息
				if(CommonUtils.notEmpty(record.getUserId())){
					String token = UUIDUtils.getUUID().replace("-", "");
					Map<String, Object> p = new HashMap<>();
					p.put("token",  token);
					int i = userAccountMapper.updateUserAccountByUserId(record.getUserId(), token);
					if(i <= 0){
						return new BaseOutJB(ErrorCode.FAILED, null);
					}else{
						return new BaseOutJB(ErrorCode.SUCCESS, p);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取用户基本信息
	 */
	protected BaseOutJB updateUserAccountByUserId(String accountId, UserInfo userInfo){
		if(CommonUtils.notEmpty(userInfo.getNickName()) && CommonUtils.notEmpty(userInfo.getFaceSrc()) 
				&& CommonUtils.notEmpty(userInfo.getSex()) && CommonUtils.notEmpty(userInfo.getIdCard())){
			UserInfoVo userInfoVo = new UserInfoVo();
			String token = UUIDUtils.getUUID().replace("-", "");
			int i = userAccountMapper.updateUserAccountByUserId(accountId, token);
			if(i <= 0){
				return new BaseOutJB(ErrorCode.FAILED, null);
			}
			userInfoVo.setToken(token);
			userInfoVo.setId(userInfo.getId());
			userInfoVo.setFaceSrc(CommonUtils.getImage(userInfo.getFaceSrc()));
			userInfoVo.setNickName(userInfo.getNickName());
			userInfoVo.setSex(userInfo.getSex());
			userInfoVo.setLocation(userInfo.getLocation());
			return new BaseOutJB(ErrorCode.SUCCESS, userInfoVo);
		}else{
			return new BaseOutJB(ErrorCode.NOT_PERFECT_USER_INFO);
		}
	}
	
	/** 绑定第三方 */
	public BaseOutJB binding(LoginInfo loginInfo) throws Exception {
		ErrorCode errorCode = null;
		if(CommonUtils.notEmpty(loginInfo.getCellPhoneNumber()) &&
				CommonUtils.notEmpty(loginInfo.getCheckCode())){
			errorCode = smsService.verifyCheckCode(loginInfo.getCellPhoneNumber(), loginInfo.getCheckCode());
			if(errorCode.getCode().equals(ErrorCode.SUCCESS.getCode())){
				boolean isExist = this.cellPhoneNumberIsExisting(loginInfo.getCellPhoneNumber()); // 查询电话号码是否存在
				if(isExist){
					UserAccount ua = new UserAccount();
					ua.setMobilePhone(loginInfo.getCellPhoneNumber());
					ua = userAccountMapper.get(ua);
					UserInfo userInfo = userInfoMapper.get(ua.getUserId());
					if(CommonUtils.notEmpty(userInfo.getId())){
						return new BaseOutJB(ErrorCode.MOBILE_BINDING_ERROR);
					}
				}else{				
					Map<String, Object> params = new HashMap<>();
					params.put("mobilePhone", loginInfo.getCellPhoneNumber());
					params.put("token", loginInfo.getToken());
					userAccountMapper.updateUserAccountByToken(params);
					return new BaseOutJB(ErrorCode.SUCCESS);
				}
			}
		}
		// 第三方绑定
		else if(CommonUtils.notEmpty(loginInfo.getAccessTokenType()) && 
				CommonUtils.notEmpty(loginInfo.getAccessToken())) {
			return this.saveAccredit(loginInfo, true);
		}
		return new BaseOutJB(errorCode);
	}
	
	/** 授权 */
	private BaseOutJB saveAccredit(LoginInfo loginInfo, boolean isbinding) throws Exception {
		BaseOutJB out = null;
		logger.info("third_part : " + loginInfo.getAccessToken() + ", third_ :" + loginInfo.getAccessTokenType());
		switch (loginInfo.getAccessTokenType()) {
			// 0:QQ 1:WX 2:WB 
			case QQ_TYPE_TOKEN:
					try {
						OpenID openIDObj = new OpenID(loginInfo.getAccessToken());
						String qqOpenId = openIDObj.getUserOpenID();
						logger.info("qqOpenId : " +qqOpenId);
						if(CommonUtils.notEmpty(qqOpenId)){
							UserAccount ua = new UserAccount();
							ua.setQqUid(qqOpenId);
							UserAccount userAccount = userAccountMapper.get(ua);
							if(isbinding){	
								if(CommonUtils.notEmpty(userAccount)) {
									return new BaseOutJB(ErrorCode.THIRD_PART_BINDING_ERROR);
								}
								Map<String, Object> params = new HashMap<>();
								params.put("qqUid", qqOpenId);
								params.put("token", loginInfo.getToken());
								userAccountMapper.updateUserAccountByToken(params);
								return new BaseOutJB(ErrorCode.SUCCESS);
							}else{
								if(CommonUtils.notEmpty(userAccount)) {
									out = this.getBaseUserInfo(userAccount);
								}else{
									return this.saveThirdUid(QQ_TYPE_TOKEN, loginInfo.getAccessToken(), qqOpenId);
								}
							}
						}else{
							logger.error("QQ登陆异常 >>>>> openIDObj is null" );
							return new BaseOutJB(ErrorCode.LOGIN_THIRD_PART_ERROR);
						}
					} catch (Exception e) {
						logger.error("QQ登陆异常 >>>>> openIDObj is null  >>> " +e.getMessage());
						return new BaseOutJB(ErrorCode.LOGIN_THIRD_PART_ERROR);
					}
				break;
			case WX_TYPE_TOKEN:
				// 先检测openId和accessToken是否有效
				com.qq.connect.utils.json.JSONObject object = WeiXinUtils.checkAuth(loginInfo.getAccessToken(),
						loginInfo.getOpenId());
				// 如果有效则继续
				if (object.getString("errcode").equals("0")) {
					// 获取用户unionId
					com.qq.connect.utils.json.JSONObject wxUserInfo = WeiXinUtils.getUserInfo(loginInfo.getAccessToken(),
							loginInfo.getOpenId());
					String unionId = wxUserInfo.getString("unionid");
					if (CommonUtils.notEmpty(unionId)) {
						UserAccount ua = new UserAccount();
						ua.setWxUid(unionId);
						UserAccount userAccount = userAccountMapper.get(ua);
						if(isbinding){
							if (CommonUtils.notEmpty(userAccount)) {
								return new BaseOutJB(ErrorCode.THIRD_PART_BINDING_ERROR);
							}
							Map<String, Object> params = new HashMap<>();
							params.put("wxUid", loginInfo.getOpenId());
							params.put("token", loginInfo.getToken());
							userAccountMapper.updateUserAccountByToken(params);
							return new BaseOutJB(ErrorCode.SUCCESS);
						}else{
							if (CommonUtils.notEmpty(userAccount)) {
								out = this.getBaseUserInfo(userAccount);
							}else{
								return this.saveThirdUid(WX_TYPE_TOKEN, loginInfo.getAccessToken(), unionId);
							}
						}
					}
				}else{
					logger.error("微信登陆异常 >>>>> " + object.toString());
					return new BaseOutJB(ErrorCode.LOGIN_THIRD_PART_ERROR);
				}
				break;
			case WB_TYPE_TOKEN:
				Account am = new Account(loginInfo.getAccessToken());
				com.kjuns.util.weibo4j.org.json.JSONObject uid = am.getUid();
				if(CommonUtils.notEmpty(uid)){
					UserAccount ua = new UserAccount();
					ua.setWbUid(uid.get("uid").toString());
					UserAccount userAccount = userAccountMapper.get(ua);
					if(isbinding){
						if (CommonUtils.notEmpty(userAccount)) {
							return new BaseOutJB(ErrorCode.THIRD_PART_BINDING_ERROR);
						}
						Map<String, Object> params = new HashMap<>();
						params.put("wbUid", uid.get("uid").toString());
						params.put("token", loginInfo.getToken());
						userAccountMapper.updateUserAccountByToken(params);
						return new BaseOutJB(ErrorCode.SUCCESS);
					}else{
						if (CommonUtils.notEmpty(userAccount)) {
							out = this.getBaseUserInfo(userAccount);
						}else{
							return this.saveThirdUid(WB_TYPE_TOKEN, loginInfo.getAccessToken(), uid.get("uid").toString());
						}
					}
				}else{
					logger.error("微博登陆异常 >>>>> uid is null");
					return new BaseOutJB(ErrorCode.LOGIN_THIRD_PART_ERROR);
				}
				break;
			default:
				break;
		}
		return out;
	}

	/** 根据电话号码获取用户相关信息 */
	private BaseOutJB getBaseUserInfo(UserAccount userAccount) throws Exception{
		UserInfoVo userInfoVo = new UserInfoVo();
		UserInfo userInfo = userInfoMapper.get(userAccount.getUserId());
		if (CommonUtils.notEmpty(userInfo)) {
			String token = UUIDUtils.getUUID().toString().replace("-", "");
			userInfoVo.setToken(token);
			userAccountMapper.updateUserAccountByUserId(userAccount.getId(), token);
			userInfoVo.setId(userInfo.getId());
			userInfoVo.setFaceSrc(userInfo.getFaceSrc());
			userInfoVo.setNickName(userInfo.getNickName());
			userInfoVo.setSex(userInfo.getSex());
			userInfoVo.setLocation(userInfo.getLocation());
		}
		return new BaseOutJB(ErrorCode.SUCCESS, userInfoVo);
	}
	
	/**第三方 uid存储*/
	private BaseOutJB saveThirdUid(String tokenType, String token, String uid) throws AuthorizeException{
		Map<String, Object> params = new HashMap<>();
		try {
			int sex = 0 ;
			String nickName = "";
			String faceIcon = "";
			String accountToken = UUIDUtils.getUUID().toString().replace("-", "");
			UserAccount userAccount = new UserAccount();
			switch (tokenType) {
				// 0:QQ 1:WX 2:WB 3:rr 4:fb 5:twi
				case QQ_TYPE_TOKEN:
					OpenID openIDObj = new OpenID(token);
					com.qq.connect.api.qzone.UserInfo qzoneUserInfo = new com.qq.connect.api.qzone.UserInfo(token, openIDObj.getUserOpenID());
			        UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
			        nickName = userInfoBean.getNickname()== null ? "": userInfoBean.getNickname().trim();
			        faceIcon = userInfoBean.getAvatar().getAvatarURL100();
			        String gender = userInfoBean.getGender();
					if(CommonUtils.notEmpty(gender) && !gender.equals("男")){
						sex= 1;
					}
					userAccount.setQqUid(uid);
					break;
				case WX_TYPE_TOKEN:
					JSONObject object= WeiXinUtils.checkAuth(token,uid);
					if(object.getString("errcode").equals("0")){
						JSONObject userInfo = WeiXinUtils.getUserInfo(token, uid);
						nickName = userInfo.getString("nickname");
		    	        faceIcon = userInfo.getString("headimgurl");
		   				if(userInfo.getInt("sex")!=1){
		   					sex= 1;
		   				}
					}
					userAccount.setWxUid(uid);
					break;
				case WB_TYPE_TOKEN:
					Users um =  new Users(token);
		    	    User user = um.showUserById(uid);
		    	    nickName = user.getName();
		    	    faceIcon = user.getAvatarLarge();
		   			gender = user.getGender();
		   			if(!gender.equals("m")){
		   				sex = 1;
		   			}
		   			userAccount.setWbUid(uid);
					break;
			}
			params.put("faceIcon", faceIcon);
			params.put("sex", sex);
			params.put("token", accountToken);
			UserInfo info = new UserInfo();
			info.setCreateDate(CommonConstants.DATETIME_SEC.format(new Date()));
			int random = (int)(Math.random()*100000);
			if(CommonUtils.notEmpty(nickName)){
				int count = userInfoMapper.getForNickNameCount(nickName);
				if (count >= 1) {
					info.setNickName(nickName + random);
				}else{
					info.setNickName(nickName);
				}
			}else{
				info.setNickName(nickName + random);
			}
			params.put("nickName", info.getNickName());
			info.setFaceSrc(faceIcon);
			info.setSex(sex);
			info.setDataFlag("1");
			userInfoMapper.insert(info);
			if(CommonUtils.notEmpty(info.getId())){
				userAccount.setToken(accountToken);
				userAccount.setUserId(info.getId());
				userAccount.setCreateDate(CommonConstants.DATETIME_SEC.format(new Date()));
				userAccount.setDataFlag("1");
				userAccountMapper.insert(userAccount);
			}
			return new BaseOutJB(ErrorCode.SUCCESS, params);
		} catch (Exception e) {
			throw new AuthorizeException(e.getMessage());
		}
	}

	/**
	 * 退出
	 */
	public ErrorCode updateAccountForToken(String token) throws Exception {
		int a = userAccountMapper.updateUserAccountByTokenNull(token);
		if(a > 0){
			return ErrorCode.SUCCESS;
		}else{
			return ErrorCode.FAILED;
		}
	}

	/**
	 * 账户是否绑定
	 */
	public BaseOutJB bindingIsExisting(String userId) throws Exception {
		UserAccount ua = new UserAccount();
		ua.setUserId(userId);
		UserAccount userAccount = userAccountMapper.get(ua);
		Map<String, Object> m = new HashMap<>();
		if(CommonUtils.notEmpty(userAccount)){
			String mobilePhone = userAccount.getMobilePhone();
			m.put("mobile", mobilePhone == null ? 0: 1);
			String qqUid = userAccount.getQqUid();
			m.put("qq", qqUid == null ? 0: 1);
			String wbUid = userAccount.getWbUid();
			m.put("wb", wbUid == null ? 0: 1);
			String wxUid = userAccount.getWxUid();
			m.put("wx", wxUid == null ? 0: 1);
		}
		return new BaseOutJB(ErrorCode.SUCCESS, m);
	}

	@Override
	public BaseOutJB complete(String cellPhoneNumber, String nickName, String faceSrc,
			String idCard) throws Exception {
		String datetime = CommonConstants.DATETIME_SEC.format(new Date());
		String id = UUIDUtils.getUUID().toString().replace("-", "");
		UserInfo userInfo = new UserInfo();
		userInfo.setFaceSrc("default_head.png");
		userInfo.setMobilePhone(cellPhoneNumber);
		userInfo.setNickName(nickName);
		userInfo.setSex(0);  //默认男
		userInfo.setCreateDate(datetime);
		userInfo.setDataFlag("1");
		userInfo.setId(id);
		int result = userInfoMapper.insert(userInfo);
		if(result > 0){
			return new BaseOutJB(ErrorCode.SUCCESS);
		}else{
			return new BaseOutJB(ErrorCode.FAILED);
		}
	}
	
}
