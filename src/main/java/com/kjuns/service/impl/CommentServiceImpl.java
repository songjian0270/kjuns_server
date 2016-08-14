package com.kjuns.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kjuns.mapper.CommentMapper;
import com.kjuns.mapper.UserInfoMapper;
import com.kjuns.mapper.VisitorMapper;
import com.kjuns.model.PageList;
import com.kjuns.model.UserComment;
import com.kjuns.model.UserInfo;
import com.kjuns.model.Visitor;
import com.kjuns.out.BaseOutJB;
import com.kjuns.service.CommentService;
import com.kjuns.util.CommonConstants;
import com.kjuns.util.CommonUtils;
import com.kjuns.util.ErrorCode;
import com.kjuns.util.UUIDUtils;
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
	
	@Autowired
	private VisitorMapper visitorMapper;
	
	private final Integer expire = 10;	//评论间隔时间
	
	/**
	 * 获取动态的评论列表
	 */
	public PageList queryContentComments(String contentId, int contentType, String type, Page page) throws Exception {
		List<ContentCommentsVo> ContentCommentsList = new ArrayList<>();
		PageList pageList = new PageList();
		String table = "";
		if(contentType == 0){
			table = CommonConstants.KJUNS_CONTENT_COMMENTS;
		}else if(contentType == 1){
			table = CommonConstants.KJUNS_CAMP_COMMENTS;
		}
		int count = commentMapper.getTotalCount(table, contentId, type);
		if(count > 0){
			page.setTotalCount(count);
			List<UserComment> list =  commentMapper.queryContentCommentsList(table, contentId, type, page.getStart(), page.getPageSize());
			if(CommonUtils.notListFEmpty(list)){
				for(UserComment comment: list){
					ContentCommentsVo comments = new ContentCommentsVo();
					comments.setId(comment.getId());
					comments.setContent(comment.getContent());
					if(comment.getUserId().equals("000000000000000000000000000000000000")){
						comments.setNickName(comment.getUserNickName());
					}else{
						UserInfo userInfo = userInfoMapper.get(comment.getUserId());
						comments.setFaceSrc(CommonUtils.getImage(userInfo.getFaceSrc()));
						comments.setNickName(userInfo.getNickName());
					}
					comments.setLikeCount(comment.getLikeCount());
					comments.setCreateDate(CommonUtils.dateToUnixTimestamp(comment.getCreateDate(), 
								CommonConstants.DATETIME_SEC));
					
					comments.setReplyCommentId(CommonUtils.getStr(comment.getReplyCommentId()));
					
					if(CommonUtils.notEmpty(comment.getReplyCommentId())){
						UserComment userReplyComment = commentMapper.get(table, comment.getReplyCommentId()); 
						comments.setReplyUserId(userReplyComment.getUserId());							
						if(userReplyComment.getUserId().equals("000000000000000000000000000000000000")){
							comments.setNickName(comment.getUserNickName());
						}else{
							UserInfo userInfo = userInfoMapper.get(userReplyComment.getUserId());
							comments.setReplyCommentId(CommonUtils.getStr(comment.getReplyCommentId()));
							comments.setReplyNickName(userInfo.getNickName());
							comments.setFaceSrc(CommonUtils.getImage(userInfo.getFaceSrc()));
						}
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
	public BaseOutJB insertContentComments(String contentId, String replyCommentId, String content, String userId, int contentType) throws Exception {
		String datetime = CommonConstants.DATETIME_SEC.format(new Date());
		String id = UUIDUtils.getUUID().toString().replace("-", "");
		UserComment userComment = new UserComment();
		userComment.setContent(content);
		userComment.setContentId(contentId);
		userComment.setId(id);
		userComment.setReplyCommentId(replyCommentId);
		userComment.setUserId(userId);
		if(expire > 0 ){
			//return new BaseOutJB(ErrorCode.NOT_QUICK_SEND_COMMENT);	
		}
		userComment.setCreateBy(userId);
		userComment.setUpdateBy(userId);
		userComment.setCreateDate(datetime);
		userComment.setUpdateDate(datetime);
		if(userId.equals("000000000000000000000000000000000000")){
			int random = new Random().nextInt(205) +1;
			Visitor visitor = new Visitor();
			visitor.setId(random);
			Visitor vtor = visitorMapper.get(visitor);
			userComment.setUserNickName(vtor.getName());
		}
		if(contentType == 0){
			userComment.setTable(CommonConstants.KJUNS_CONTENT_COMMENTS);
		}else if(contentType == 1){
			userComment.setTable(CommonConstants.KJUNS_CAMP_COMMENTS);
		}
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
	public boolean delContentCommentById(String contentId, String id, String userId, int contentType){
		String datetime = CommonConstants.DATETIME_SEC.format(new Date());
		UserComment userComment = new UserComment();
		userComment.setId(id);
		userComment.setContentId(contentId);
		userComment.setUpdateBy(userId);
		userComment.setUpdateDate(datetime);
		if(contentType == 0){
			userComment.setTable(CommonConstants.KJUNS_CONTENT_COMMENTS);
		}else if(contentType == 1){
			userComment.setTable(CommonConstants.KJUNS_CAMP_COMMENTS);
		}
		int result = commentMapper.delContentCommentsById(userComment);
		return result > 0 ? true:false;
	}
	
	/**
	 * 点赞
	 * @param comment
	 * @return
	 */
	public boolean insertContentCommentsLike(String id, String userId, int contentType){
		String table = "";
		if(contentType == 0){
			table = CommonConstants.KJUNS_CONTENT_COMMENTS;
		}else if(contentType == 1){
			table = CommonConstants.KJUNS_CAMP_COMMENTS;
		}
		int result = commentMapper.insertContentCommentsLike(table, id);
		return result > 0 ? true:false;
	}
	
}
