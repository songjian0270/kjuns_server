package com.kjuns.service;

import java.util.List;

import com.kjuns.model.UserFaq;
import com.kjuns.util.ErrorCode;
import com.kjuns.vo.BannerVo;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-8-25
 * @file CommonService.java
 * @package com.kjuns.service
 * @project kjuns
 * @version 2.0
 */
public interface CommonService {
	
	/**
	 * 获取banner
	 * @return
	 * @throws Exception
	 */
	List<BannerVo> queryBanner() throws Exception;
	
	/**
	 * 获取FAQ
	 * @return
	 * @throws Exception
	 */
	List<UserFaq> queryFAQ() throws Exception;
	
	/**
	 * 保存FAQ
	 * @return
	 * @throws Exception
	 */
	boolean saveFaq(UserFaq userFaq) throws Exception;
	
	/**
	 * 举报
	 * @param userId
	 * @param contentId
	 * @param contentType
	 * @return
	 * @throws Exception
	 */
	ErrorCode insertReport(String userId, String contentId, String contentType) throws Exception;

}
