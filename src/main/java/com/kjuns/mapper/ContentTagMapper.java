package com.kjuns.mapper;

import java.io.Serializable;
import java.util.List;

import com.kjuns.model.ContentTag;

public interface ContentTagMapper extends Serializable {
	
	List<ContentTag> queryContentTagForContentId(String contentId);
	
	void addContentTag(ContentTag ContentTag);

}
