package com.kjuns.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PageList {
	
	private List<?> list = new ArrayList<>();
	
	private int totalCount;
	
	private int pageInvertedIndex;
	
//	private int pageCount;

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageInvertedIndex() {
		return pageInvertedIndex;
	}

	public void setPageInvertedIndex(int pageInvertedIndex) {
		this.pageInvertedIndex = pageInvertedIndex;
	}


}
