package com.kjuns.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kjuns.mapper.SectionMapper;
import com.kjuns.model.ContentSectionSubscribe;
import com.kjuns.service.SectionService;
import com.kjuns.util.CommonConstants;
import com.kjuns.util.UUIDUtils;

@Service("sectionService")
public class SectionServiceImpl implements SectionService {

	@Autowired
	private SectionMapper sectionMapper;
	
	@Override
	public void subscribe(String userId, String sectionId) {
		String datetime = CommonConstants.DATETIME_SEC.format(new Date());
	    ContentSectionSubscribe subscribe = new ContentSectionSubscribe();
	    
	    String token = UUIDUtils.getUUID().toString().replace("-", "");
	    
		subscribe.setCreateBy(userId);
		subscribe.setCreateDate(datetime);
		subscribe.setDataFlag("1");
		subscribe.setUpdateBy(userId);
		subscribe.setUpdateDate(datetime);
		subscribe.setUserId(userId);
		subscribe.setSectionId(sectionId);
		subscribe.setId(token);
		
		sectionMapper.insertSubscribe(subscribe);
	}

}
