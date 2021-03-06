package com.kjuns.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kjuns.mapper.CommentMapper;
import com.kjuns.mapper.UserInfoMapper;
import com.kjuns.model.PageList;
import com.kjuns.model.UserComment;
import com.kjuns.model.UserInfo;
import com.kjuns.out.BaseOutJB;
import com.kjuns.service.CommentService;
import com.kjuns.util.CommonConstants;
import com.kjuns.util.CommonUtils;
import com.kjuns.util.ErrorCode;
import com.kjuns.util.pager.Page;
import com.kjuns.vo.ContentCommentsVo;

/**
 * <b>Function: </b>
 * 
 * @author James
 * @date 2015-9-6
 * @file CommonServiceImpl.java
 * @package com.kjuns.service.impl
 * @project kjuns
 * @version 2.0
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	private final Integer expire = 10;	//评论间隔时间
	
	/**
	 * 获取动态的评论列表
	 */
	public PageList queryContentComments(String contentId, Page page) throws Exception {
		List<ContentCommentsVo> ContentCommentsList = new ArrayList<>();
		PageList pageList = new PageList();
		int count = commentMapper.getTotalCount(contentId);
		if(count > 0){
			page.setTotalCount(count);
			List<UserComment> list =  commentMapper.queryContentCommentsList(contentId, page.getStart(), page.getPageSize());
			if(CommonUtils.notListFEmpty(list)){
				for(UserComment comment: list){
					ContentCommentsVo comments = new ContentCommentsVo();
					UserInfo userInfo = userInfoMapper.get(comment.getUserId());
					comments.setFaceSrc(userInfo.getFaceSrc());
					comments.setNickName(userInfo.getNickName());
					comments.setCreateDate(CommonUtils.dateToUnixTimestamp(comment.getCreateDate(), 
								CommonConstants.DATETIME_SEC));
					
					if(CommonUtils.notEmpty(comment.getReplyCommentId())){
						UserComment userReplyComment = commentMapper.get(comment.getReplyCommentId()); 
						comments.setReplyUserId(userReplyComment.getUserId());
						userInfo = userInfoMapper.get(userReplyComment.getUserId());
						comments.setReplyNickName(userInfo.getNickName());
						comments.setReplyContent(userReplyComment.getContent());
						comments.setReplyCreateDate(CommonUtils.dateToUnixTimestamp(userReplyComment.getCreateDate(), 
								CommonConstants.DATETIME_SEC));
					}

					ContentCommentsList.add(comments);
				}
			}
		}
		pageList.setPageInvertedIndex(page.getReturnIndex());
		pageList.setTotalCount(count);
		pageList.setList(ContentCommentsList);
		return pageList;
	}
	

	// 评论
	public BaseOutJB insertContentComments(String contentId, String replyCommentId, String content, String userId) throws Exception {
		String datetime = CommonConstants.DATETIME_SEC.format(new Date());
		UserComment userComment = new UserComment();
		userComment.setContent(content);
		userComment.setContentId(contentId);
		userComment.setReplyCommentId(replyCommentId);
		userComment.setUserId(userId);
		if(expire > 0 ){
			//return new BaseOutJB(ErrorCode.NOT_QUICK_SEND_COMMENT);	
		}
		userComment.setCreateBy(userId);
		userComment.setUpdateBy(userId);
		userComment.setCreateDate(datetime);
		userComment.setUpdateDate(datetime);
		
		boolean f = commentMapper.insertContentComments(userComment) >= 1 ? true: false;
		if(f){
			return new BaseOutJB(ErrorCode.SUCCESS);
		}else{
			return new BaseOutJB(ErrorCode.FAILED);
		}
	}
	
	/**
	 * 删除通用评论
	 * @param comment
	 * @return
	 */
	public boolean delContentCommentById(String contentId, String id, String userId){
		String datetime = CommonConstants.DATETIME_SEC.format(new Date());
		UserComment userComment = new UserComment();
		userComment.setId(id);
		userComment.setContentId(contentId);
		userComment.setUpdateBy(userId);
		userComment.setUpdateDate(datetime);
		int result = commentMapper.delContentCommentsById(userComment);
		return result > 0 ? true:false;
	}
	
}
