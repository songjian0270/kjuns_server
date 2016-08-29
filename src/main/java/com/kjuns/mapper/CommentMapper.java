package com.kjuns.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kjuns.model.UserComment;

public interface CommentMapper {
	
	int getTotalCount(@Param("table")String table, @Param(value="contentId")String contentId,
			@Param(value="type")String type);
	
	List<UserComment> queryContentCommentsList(@Param("table")String table,
			@Param(value="contentId")String contentId,
			@Param(value="type")String type, @Param(value="pageNo")int pageNo, 
			@Param(value="pageSize")int pageSize);
 	
	/**
	 * 删除一条动态评论
	 * @param id
	 * @return
	 */
	int delContentCommentsById(UserComment userComment);

	/**
	 * 插入一条评论
	 * @param comment
	 * @return
	 */
	int insertContentComments(UserComment userComment);
	
	/**
	 * 点赞
	 * @param table
	 * @param id
	 * @param 增加的点赞数量
	 * @return
	 */
	int insertContentCommentsLike(@Param("table")String table, @Param("id")String id,@Param(value="count")int count);
	
	
	
	UserComment get(@Param("table")String table, @Param(value="id")String id);
	
}
