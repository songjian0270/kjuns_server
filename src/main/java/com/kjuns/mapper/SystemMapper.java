package com.kjuns.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-8-27
 * @file SystemMapper.java
 * @package com.kjuns.mapper
 * @project kjuns
 * @version 2.0
 */
public interface SystemMapper {
	
	 void deleteAccount(@Param("cellPhoneNumber")String cellPhoneNumber) throws Exception;
	 
	 void deleteUserInfo(@Param("id")String id) throws Exception;
	 
	 String getAccount(@Param("cellPhoneNumber")String cellPhoneNumber) throws Exception;
	 
}
