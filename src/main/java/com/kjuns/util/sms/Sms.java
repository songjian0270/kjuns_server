package com.kjuns.util.sms;

import java.net.URL;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

/**
 * <b>Function: </b>  获取soap请求连接的接口类
 * @author James
 * @date 2015-04-12
 * @file Sms.java
 * @package com.gtbang.util.sms
 * @project gtbang
 * @version 1.0
 */
public interface Sms extends Service {
	
     String getSmsSoapAddress();

     SmsSoap getSmsSoap() throws javax.xml.rpc.ServiceException;

     SmsSoap getSmsSoap(URL portAddress) throws ServiceException;
     
}
