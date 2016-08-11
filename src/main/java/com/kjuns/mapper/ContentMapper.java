package com.kjuns.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kjuns.model.Content;
import com.kjuns.vo.ContentVo;

public interface ContentMapper {
	
	int getTotalCount(@Param(value="typeId")String typeId);
	
	List<Content> queryContentList(@Param(value="typeId")String typeId,
			@Param(value="pageNo")int pageNo, @Param(value="pageSize")int pageSize);
	
	/**
	 * 删除内容
	 * @param id
	 * @return
	 */
	int delContentById(Content Content);

	/**
	 * 插入内容
	 * @param comment
	 * @return
	 */
	int insertContent(Content Content);
	
	/**
	 * 点赞
	 * @param table
	 * @param id
	 * @return
	 */
	int insertContentLike(@Param("id")String id);
	
	/**
	 * 分享
	 * @param table
	 * @param id
	 * @return
	 */
	int insertContentShare(@Param("id")String id);
	
	/**
	 * 获取文章详情
	 * @param id
	 * @return
	 */
	ContentVo selectById(@Param("id")String id);
	
}
