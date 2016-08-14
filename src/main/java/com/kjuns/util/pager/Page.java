package com.kjuns.util.pager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean类 - 分页
 * 
 * @author James
 *
 */

@SuppressWarnings({"rawtypes","unused"})
public class Page implements Serializable {

	private static final long serialVersionUID = -5925291555361274029L;

	// 排序方式
	public enum OrderType {
		asc, desc
	}

	public static final Integer MAX_PAGE_SIZE = 100;// 每页最大记录数限制

	private Integer pageNumber = 1;   // 当前页码
	private Integer pageResult = 0;   // 当前记录起始索引
	private Integer pageInvertedIndex = 0;
	private Integer returnIndex = 0;
	// private Integer rowStart = 0 ; // 当前页第一行数据在数据库中的行号
	private Integer pageSize = 10;    // 每页记录数
	private Integer totalCount = 0;   // 总记录数
	private Integer pageCount = 0;    // 总页数

	private Integer start = 0;
	private Integer end = 0;

	private boolean entityOrField; // true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属

	private String property; // 查找属性名称
	private String keyword; // 查找关键字
	private String orderBy = "createTime"; // 排序字段
	private OrderType orderType = OrderType.desc;// 排序方式
	private List list; // 数据List

	private Integer nextPage; // 下一页

	private Integer upPage; // 上一页
	
	public Page(){
		
	}
	
	public Page(Integer _pageInvertedIndex,Integer _pageSize){
		this.setPageInvertedIndex(_pageInvertedIndex);
		this.setPageSize(_pageSize);
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		if (pageNumber == null) {
			pageNumber = 1;
		}

		if (pageNumber < 1) {
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
		// rowStart = (pageNumber - 1) * getPageSize();
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if (pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageCount() {
		pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount++;
		}
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Integer getNextPage() {
		if (this.getPageCount() > this.pageNumber) {
			return this.pageNumber + 1;
		} else {
			return 1;
		}
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getUpPage() {
		if (this.pageNumber > 1)
			return this.pageNumber - 1;
		else
			return this.pageCount;
	}

	public void setUpPage(Integer upPage) {
		this.upPage = upPage;
	}

	public Integer getPageResult() {
		return this.getPageNumber() - 1;
	}

	public void setPageResult(Integer pageResult) {
		this.pageResult = pageResult;
	}

	public boolean isEntityOrField() {
		return entityOrField;
	}

	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}

	public Integer getStart() {
		//return start = this.getPageResult() * this.getPageSize();
		if(this.pageInvertedIndex == 0){
			return start = 0;
		}else{
			return start = (this.getTotalCount() - this.pageInvertedIndex) + 1;	
		}
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		this.end = this.getStart() + this.getPageSize();
		if (this.end == 0) {
			return this.getPageSize() -1;
		} else {
			return end  - 1;
		}
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public List getPageList() {
		int fromIndex = 0; // 从哪里开始截取
		int toIndex = 0; // 截取几个
		if (list == null || list.size() == 0)
			return null;
		// 当前页小于或等于总页数时执行
		if (this.pageInvertedIndex <= this.getTotalCount()) {
			if(this.pageInvertedIndex > 0){ 
				fromIndex = (this.getTotalCount() - this.pageInvertedIndex) +1;
			}
			if(pageSize > this.getTotalCount()){
				toIndex = (fromIndex + this.getTotalCount() );
			}else{
				toIndex = (fromIndex + pageSize );
			}
			if(toIndex>this.getTotalCount()){
				toIndex = this.getTotalCount();
			}
		}
		return list.subList(fromIndex , toIndex);
	}
	
	public List getPageList(Long id) {
		List<String> result = new ArrayList<>();
		if (list == null || list.size() == 0)
			return null;
		if (pageNumber <= getPageCount() && pageNumber != 0) {
			for(Object l: list){
				if(Long.valueOf((String) l) > id){
					result.add((String)l);
				}
			}
		}
		return result;
	}

	public Integer getPageInvertedIndex() {
//		if(this.getTotalCount() != 0){
//			return this.getTotalCount() - (this.getPageSize() - 1);
//		}else{
			return pageInvertedIndex;
//		}
	}

	public void setPageInvertedIndex(Integer pageInvertedIndex) {
		this.pageInvertedIndex = pageInvertedIndex;
	}

	public Integer getReturnIndex() {
		Integer index = this.getTotalCount() - this.getEnd();
		if(index < 0){
			return 0;
		}else{
			return index;
		}
	}

	public void setReturnIndex(Integer returnIndex) {
		this.returnIndex = returnIndex;
	}

}
