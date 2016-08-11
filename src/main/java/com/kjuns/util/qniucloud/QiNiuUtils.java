package com.kjuns.util.qniucloud;

import com.kjuns.util.SysConf;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;

public class QiNiuUtils {
	
	private PutPolicy putPolicy;
	private RSClient client;


	private static QiNiuUtils instance = null;

	public void reloadConfig() throws Exception{
    	init();
    }
    
	private QiNiuUtils() throws Exception {
		init();
	}
	
	public static QiNiuUtils getInstance() throws Exception {
		if (instance == null) {
			synchronized (QiNiuUtils.class) {
				if (instance == null) {
					instance = new QiNiuUtils();
				}
			}
		}
		return instance;
	}
	
	private void init() throws Exception {
        putPolicy = new PutPolicy( SysConf.QN_BUCKET_NAME);
	}
	
	public String getNewToken() throws Exception{		
		return putPolicy.token(new Mac(SysConf.QN_ACCESS_KEY, SysConf.QN_SECRET_KEY));
	}
	
	public String getPicToken(String uploaderId) throws Exception{
		putPolicy.saveKey = "STUDENT_" 
							+ "PNG_" 
							+ uploaderId 
							+ "_$(year)$(mon)$(day)$(hour)$(min)$(sec)_"
							+ "$(etag)"
							+ "$(ext)";
		return putPolicy.token(new Mac(Config.ACCESS_KEY, Config.SECRET_KEY));
	}
	
	public String getVideoToken(String uploaderId) throws Exception{
		putPolicy.saveKey = "STUDENT_" 
							+ "MP4_" 
							+ uploaderId 
							+ "_$(year)$(mon)$(day)$(hour)$(min)$(sec)_"
							+ "$(etag)"
							+ "$(ext)";
		
		return putPolicy.token(new Mac(Config.ACCESS_KEY, Config.SECRET_KEY));
	}
	
	public int deleteFile(String bucketName,String key){
		client = new RSClient(new Mac(Config.ACCESS_KEY, Config.SECRET_KEY));
        return client.delete(SysConf.QN_BUCKET_NAME, key).getStatusCode();
	}

}
