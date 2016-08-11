package com.kjuns.task; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheManageTask {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private int cacheSize;
	
	public void execute(){
		logger.info("CacheManageTask execute...");

		/**
		for (Map.Entry<String, RandomCode> entry: CommonCache.MEM_REG_SMSCODE_MAPPING.entrySet()) {
			RandomCode smsCode = entry.getValue();
			//删除10分钟以前的缓存信息
			if((System.currentTimeMillis() - smsCode.getTime()) > 600000){
				CommonCache.MEM_REG_SMSCODE_MAPPING.remove(entry.getKey());
			} 
		}
		
		for (Map.Entry<String, RandomCode> entry: CommonCache.MEM_FORGET_PWD_RANDOM_CODE.entrySet()) {
			RandomCode smsCode = entry.getValue();
			//删除30分钟以前的缓存信息
			if((System.currentTimeMillis() - smsCode.getTime()) > 1800000){
				CommonCache.MEM_FORGET_PWD_RANDOM_CODE.remove(entry.getKey());
			}
		}
		
		for (Map.Entry<String, RandomCode> entry: CommonCache.ADD_CARD_SMSCODE_MAPPING.entrySet()) {
			RandomCode smsCode = entry.getValue();
			//删除10分钟以前的缓存信息
			if((System.currentTimeMillis() - smsCode.getTime()) > 600000){
				CommonCache.ADD_CARD_SMSCODE_MAPPING.remove(entry.getKey());
			}
		}
		**/
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}
	
	
}
 