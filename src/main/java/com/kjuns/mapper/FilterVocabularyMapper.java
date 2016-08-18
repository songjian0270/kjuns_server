package com.kjuns.mapper;

import org.apache.ibatis.annotations.Param;

public interface FilterVocabularyMapper {
	
	int getTotalCount(@Param(value="name")String name);
	
}
