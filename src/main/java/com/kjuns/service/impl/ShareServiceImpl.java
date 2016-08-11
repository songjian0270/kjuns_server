package com.kjuns.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kjuns.mapper.ShareMapper;
import com.kjuns.mapper.UserInfoMapper;
import com.kjuns.model.PageList;
import com.kjuns.model.UserInfo;
import com.kjuns.model.UserShare;
import com.kjuns.service.ShareService;
import com.kjuns.util.CommonConstants;
import com.kjuns.util.CommonUtils;
import com.kjuns.util.pager.Page;
import com.kjuns.vo.ContentShareVo;


@Service("shareService")
public class ShareServiceImpl implements ShareService{

	@Autowired 
	private ShareMapper shareMapper;
	
	@Autowired
	private UserInfoMapper userInfoMapper;

	public boolean insertUserShare(String id, String channel, String url, String userId) throws Exception {

		String datetime = CommonConstants.DATETIME_SEC.format(new Date());
		UserShare userShare = new UserShare();

		userShare.setCreateBy(userId);
		userShare.setCreateDate(datetime);
		userShare.setDataFlag("1");
		userShare.setUpdateBy(userId);
		userShare.setUpdateDate(datetime);
		userShare.setContentId(id);
		userShare.setChannel(channel);
		userShare.setUrl(url);
	
		int result = shareMapper.insertContentShare(userShare);
		if(result <= 0){
			return false;
		}	
		return true;	
	}

	/**
	 * 分享列表
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public PageList queryShareUser(String contentId, Page page) throws Exception {
		PageList pageList = new PageList();
		List<ContentShareVo> list = new ArrayList<>();
		int count = shareMapper.getTotalCount(contentId);
		page.setTotalCount(count);
		List<UserShare> shareList = shareMapper.queryContentShareList(contentId, page.getStart(), page.getPageSize());
		for(UserShare share: shareList){
			ContentShareVo ucs = new ContentShareVo();
			UserInfo userInfo = userInfoMapper.get(share.getUserId());
			ucs.setFaceSrc(userInfo.getFaceSrc());
			ucs.setNickName(userInfo.getNickName());
			ucs.setUserId(share.getUserId());
			ucs.setCreateDate(CommonUtils.dateToUnixTimestamp(
					share.getCreateDate(), CommonConstants.DATETIME_SEC));

			list.add(ucs);
		}
		pageList.setTotalCount(count);
		pageList.setList(list);
		pageList.setPageInvertedIndex(page.getReturnIndex());
		return pageList;
	}
	
}
