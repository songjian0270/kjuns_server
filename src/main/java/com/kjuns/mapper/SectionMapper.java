package com.kjuns.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kjuns.model.ContentSection;

public interface SectionMapper {
	
	int getTotalCount();
	
	List<ContentSection> queryContentSectionList(@Param(value="pageNo")int pageNo, 
			@Param(value="pageSize")int pageSize);
	
}
