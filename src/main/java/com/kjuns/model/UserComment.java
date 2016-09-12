package com.kjuns.model;

public class UserComment extends BaseModel {

	private String userId;
	
	private String contentId;
	
	private String content;
	
	private String replyCommentId;
	
	private String replyUserNickName;
	
	private long likeCount;
	
	private String userNickName;
	
	private String table;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReplyCommentId() {
		return replyCommentId;
	}

	public void setReplyCommentId(String replyCommentId) {
		this.replyCommentId = replyCommentId;
	}

	public String getReplyUserNickName() {
		return replyUserNickName;
	}

	public void setReplyUserNickName(String replyUserNickName) {
		this.replyUserNickName = replyUserNickName;
	}

	public long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

}
