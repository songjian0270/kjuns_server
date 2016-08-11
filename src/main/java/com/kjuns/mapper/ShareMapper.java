package com.kjuns.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kjuns.model.UserShare;


public interface ShareMapper {
	
	int getTotalCount(@Param(value="contentId")String contentId);
	
	List<UserShare> queryContentShareList(@Param(value="contentId")String contentId,
			@Param(value="pageNo")int pageNo, @Param(value="pageSize")int pageSize);
	
	/**
	 * 保存分享
	 * @return
	 */
	int insertContentShare(UserShare userShare);
		
}
