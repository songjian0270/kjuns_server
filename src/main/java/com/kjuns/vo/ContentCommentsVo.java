package com.kjuns.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ContentCommentsVo {
	
	private String nickName;
	
	private String faceSrc;
	
	private String content;
	
	private Integer level;
	
	private String userId;
	
	private String replyCommentId;
	
	private String replyUserId;
	
	private String replyFaceSrc;
	
	private Long replyCreateDate=0l;
	
	private String replyContent="";
	
	private String replyNickName = "";
	
	private Long createDate=0l;
	
	private long likeCount;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFaceSrc() {
		return faceSrc;
	}

	public void setFaceSrc(String faceSrc) {
		this.faceSrc = faceSrc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReplyCommentId() {
		return replyCommentId;
	}

	public void setReplyCommentId(String replyCommentId) {
		this.replyCommentId = replyCommentId;
	}

	public String getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}

	public Long getReplyCreateDate() {
		return replyCreateDate;
	}

	public void setReplyCreateDate(Long replyCreateDate) {
		this.replyCreateDate = replyCreateDate;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReplyNickName() {
		return replyNickName;
	}

	public void setReplyNickName(String replyNickName) {
		this.replyNickName = replyNickName;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public String getReplyFaceSrc() {
		return replyFaceSrc;
	}

	public void setReplyFaceSrc(String replyFaceSrc) {
		this.replyFaceSrc = replyFaceSrc;
	}

	public long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}
	
}
