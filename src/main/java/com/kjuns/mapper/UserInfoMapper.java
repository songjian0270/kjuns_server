package com.kjuns.mapper;

import org.apache.ibatis.annotations.Param;

import com.kjuns.model.UserInfo;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-8-18
 * @file BaseUserInfoMapper.java
 * @package com.kjuns.mapper
 * @project kjuns
 * @version 2.0
 */
public interface UserInfoMapper {
	
	UserInfo get(@Param("userId")String userId);
	
	UserInfo getForToken(@Param("token")String token);
	
	int insert(UserInfo baseUserInfo);
	
	int updateUserInfoById(UserInfo userInfo);
	
	int updateDescriptionById(@Param("description")String description, @Param("userId")Long userId);
	
	int getForNickNameCount(@Param("nickName")String nickName);

}
