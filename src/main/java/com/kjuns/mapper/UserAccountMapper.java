package com.kjuns.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kjuns.model.UserAccount;

public interface UserAccountMapper {
	
	UserAccount get(UserAccount userAccount);
	
	int insert(UserAccount userAccount);
	
	int updateUserAccountByToken(Map<String, Object> params);
	
	int updateUserAccountByUserId(@Param("userId")String userId, @Param("token")String token);
	
	int updateUserAccountByAccountId(Map<String, Object> params);
	
	int updateUserAccountByThirdUid(UserAccount userAccount);

	int updateUserAccountByTokenNull(@Param("token")String token);
	
}
