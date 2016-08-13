package com.kjuns.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kjuns.mapper.CommonMapper;
import com.kjuns.model.Banner;
import com.kjuns.model.UserFaq;
import com.kjuns.service.CommonService;
import com.kjuns.util.CommonConstants;
import com.kjuns.util.CommonUtils;
import com.kjuns.util.ErrorCode;
import com.kjuns.vo.BannerVo;

/**
 * <b>Function: </b>
 * 
 * @author James
 * @date 2015-8-27
 * @file CommonServiceImpl.java
 * @package com.kjuns.service.impl
 * @project kjuns
 * @version 2.0
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonMapper commonMapper;
	
	@Override
	public List<UserFaq> queryFAQ() throws Exception {
		return commonMapper.queryFaq();
	}

	public boolean saveFaq(UserFaq userFaq) throws Exception {
		userFaq.setDataFlag("1");
		String datetime = CommonConstants.DATETIME_SEC.format(new Date());
		userFaq.setCreateDate(datetime);
		userFaq.setUpdateDate(datetime);
		commonMapper.insertFaq(userFaq);
		return true;
	}

	@Override
	public List<BannerVo> queryBanner() throws Exception {
		List<Banner> banners = commonMapper.queryBanner();
		List<BannerVo> list = new ArrayList<BannerVo>();
		for(Banner banner: banners){
			BannerVo vo = new BannerVo();
			vo.setId(banner.getId());
			vo.setContent(banner.getContent());
			vo.setTitle(banner.getTitle());
			vo.setBackground(CommonUtils.getImage(banner.getBackground()));
			vo.setUrl(banner.getUrl());
			list.add(vo);
		}
		return list;
	}

	@Override
	public ErrorCode insertReport(String userId, String reportId, String reportType) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("reportId", reportId);
		params.put("reportType", reportType);
		String dateTime = CommonConstants.DATETIME_SEC.format(new Date());
		params.put("createDate", dateTime);
		params.put("updateDate", dateTime);
		params.put("createBy", userId);
		params.put("updateBy", userId);
		return commonMapper.insertReport(params) > 0 ? ErrorCode.SUCCESS : ErrorCode.SYS_ERROR;
	}

}
