package com.kjuns.mapper;

import java.util.List;
import java.util.Map;

import com.kjuns.model.Banner;
import com.kjuns.model.UserFaq;

public interface CommonMapper {
	
	/**
	 * 保存FAQ
	 * @param userFaq
	 * @return
	 * @throws Exception
	 */
	int insertFaq(UserFaq userFaq) throws Exception;
	
	List<UserFaq> queryFaq() throws Exception;
	
	int insertReport(Map<String, Object> params) throws Exception;
	
	List<Banner> queryBanner() throws Exception;
	
}
