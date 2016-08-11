package com.kjuns.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA.
 * 将数据源的键存入ThreadLocal中
 */
public class DataSourceKeyHolder {
	
    private static Logger logger = LoggerFactory.getLogger(DataSourceKeyHolder.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDataSourceKey(String dataSourceKey) {
        contextHolder.set(dataSourceKey);
    }

    public static String getDataSourceKey() {
        String key = contextHolder.get();
        logger.info("Thread:"+Thread.currentThread().getName()+" dataSource key is "+ key);
        return key;
    }

    public static void clearDataSourceKey() {
        contextHolder.remove();
    }
}
