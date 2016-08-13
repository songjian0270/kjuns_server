package com.kjuns.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kjuns.model.Content;

public interface CampMapper extends Serializable {
	
	int selectByTitle(@Param(value="title")String title);

	int getTotalCount();

	List<Content> queryCampList(@Param(value="pageNo")int pageNo, @Param(value="pageSize")int pageSize);

	Content selectById(String id);

	void deleteCamp(Content content);

	int insertCamp(Content content);
	
}