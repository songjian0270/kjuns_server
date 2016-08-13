package com.kjuns.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kjuns.model.BaseModel;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-8-27
 * @file BannerVo.java
 * @package com.idol.vo
 * @project idol
 * @version 2.0
 */
@JsonInclude(Include.NON_NULL)
public class BannerVo extends BaseModel{
	
	private String title;
	
	private String content;
	
	private String url;
	
	private Integer sortIndex;
	
	private String background;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

}
