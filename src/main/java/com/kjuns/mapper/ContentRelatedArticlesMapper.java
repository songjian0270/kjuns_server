package com.kjuns.mapper;

import java.io.Serializable;
import java.util.List;

import com.kjuns.model.ContentRelatedArticles;

public interface ContentRelatedArticlesMapper extends Serializable {
	
	List<ContentRelatedArticles> queryContentRelatedArticlesForContentId(String contentId);
	
	void addContentRelatedArticles(ContentRelatedArticles contentRelatedArticles);
	
	void deleteContentRelatedArticles(ContentRelatedArticles contentRelatedArticles);

	void updateContentRelatedArticles(ContentRelatedArticles contentRelatedArticles);
	
}
