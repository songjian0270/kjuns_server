package com.kjuns.service;

import java.util.Map;

import com.kjuns.out.BaseOutJB;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-8-27
 * @file SystemService.java
 * @package com.kjuns.service
 * @project kjuns
 * @version 2.0
 */
public interface SystemService {
	
	BaseOutJB getUpgradeInfo(String userAgent);
	
	Map<String, Object> getConfigConstants(Double levelVer);
	
}
