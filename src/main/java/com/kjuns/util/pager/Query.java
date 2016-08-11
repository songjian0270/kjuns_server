package com.kjuns.util.pager;

import java.util.Map;

/**
 * <b>Function: </b>  封装查询条件  及分页参数   
 * @author James
 * @date 2015-07-28
 * @file Query.java
 * @package com.kjuns.until
 * @project kjuns
 * @version 2.0
 */
public class Query {
	
	private Map<String, Object> queryParams;
	
	private Page page;

	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, Object> queryParams) {
		this.queryParams = queryParams;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
}

