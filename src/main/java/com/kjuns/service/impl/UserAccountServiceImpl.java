package com.kjuns.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kjuns.mapper.UserAccountMapper;
import com.kjuns.model.UserAccount;
import com.kjuns.service.UserAccountService;

/**
 * <b>Function: </b>
 * 
 * @author James
 * @date 2015-07-28
 * @file UserAccountImpl.java
 * @package com.kjuns.service.impl
 * @project kjuns
 * @version 2.0
 */
@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {

	public Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserAccountMapper userAccountMapper;

	@Override
	public UserAccount get(UserAccount userAccount) {
		return userAccountMapper.get(userAccount);
	}

	
}
