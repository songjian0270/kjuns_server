package com.kjuns.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kjuns.model.UserComment;

public interface CommentMapper {
	
	int getTotalCount(@Param(value="contentId")String contentId);
	
	List<UserComment> queryContentCommentsList(@Param(value="contentId")String contentId,
			@Param(value="pageNo")int pageNo, @Param(value="pageSize")int pageSize);
 	
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
	 * @return
	 */
	int insertContentCommentsLike(@Param("id")String id);
	
	UserComment get(@Param(value="id")String id);
	
}
