package com.kjuns.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.kjuns.out.BaseOutJB;
import com.kjuns.service.SystemService;
import com.kjuns.util.CommonConstants;
import com.kjuns.util.ErrorCode;
import com.kjuns.util.SysConf;
import com.kjuns.vo.UpgradeVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-8-27
 * @file SytemServiceImpl.java
 * @package com.kjuns.service.impl
 * @project kjuns
 * @version 2.0
 */
@Service("systemService")
public class SytemServiceImpl implements SystemService{
	
	/** version - forceUpdate */
	public static Map<Integer,Integer> andriodVerMap = new HashMap<>();
	/** version - forceUpdate */
	public static Map<Integer,Integer> iosVerMap = new HashMap<>();
	
	@Override
	public BaseOutJB getUpgradeInfo(String userAgent) {
		
//		if(!userAgent.matches(VERSION_FORMAT_REG)){
//			return new BaseOutJB(ErrorCode.CLIENT_VERSION_LOW);
//		}
		
		UpgradeVo upgradeVo = new UpgradeVo();
		int idx = userAgent.indexOf(":");
		String versionNo =userAgent.substring(0, idx);//获得版本号
		String platform = userAgent.substring(idx+1, userAgent.length());//平台
		
		String lastestVer = null;
		
		if("Android".equals(platform)){
			//安卓
			lastestVer = SysConf.ANDRIOD_LATEST_VER;
			upgradeVo.setLatestVer(SysConf.ANDRIOD_LATEST_VER);
			upgradeVo.setDownLink(SysConf.ANDRIOD_DOWN_LINK);
			upgradeVo.setDiscription(SysConf.ANDRIOD_DISCRIPTION);
			if(versionNo == null){
				upgradeVo.setIsUpdate(CommonConstants.IS_UPDATE_NOW);
			} else {
				if (ver(SysConf.ANDRIOD_IS_UPDATE, versionNo)){
					upgradeVo.setIsUpdate(CommonConstants.IS_UPDATE_NOW);
				} else {
					upgradeVo.setIsUpdate(CommonConstants.NO_UPDATE_NOW);
				}
			}
		}else{
			//苹果
			lastestVer = SysConf.IOS_LATEST_VER;
			upgradeVo.setLatestVer(SysConf.IOS_LATEST_VER);
			upgradeVo.setDownLink(SysConf.IOS_DOWN_LINK);
			upgradeVo.setDiscription(SysConf.IOS_DISCRIPTION);
			String jspatchFileRsaKey = haveJSPatch(versionNo);
			if(jspatchFileRsaKey!=null){
				upgradeVo.setIsNeedJS(true);
				upgradeVo.setJsKey(jspatchFileRsaKey);
			}else{
				upgradeVo.setIsNeedJS(false);
			}

			if(versionNo == null){
				upgradeVo.setIsUpdate(CommonConstants.IS_UPDATE_NOW);
			} else {
				if (ver(SysConf.IOS_IS_UPDATE, versionNo)){
					upgradeVo.setIsUpdate(CommonConstants.IS_UPDATE_NOW);
				} else {
					upgradeVo.setIsUpdate(CommonConstants.NO_UPDATE_NOW);
				}
			}
		}
		
		if(versionNo == null){
			return new BaseOutJB(ErrorCode.CLIENT_VERSION_LOW, upgradeVo);
		}else{
			if(ver(lastestVer, versionNo)){
				return new BaseOutJB(ErrorCode.CLIENT_VERSION_LOW, upgradeVo);	
			}
		}
		return new BaseOutJB(ErrorCode.SUCCESS, upgradeVo);
	}
	
	/**
	 * 判断该版本是否拥有jspatch热补丁
	 * @param versionNo 当前版本号
	 * @return 返回js文件key值
	 */
	private String haveJSPatch(String versionNo){
		JSONArray array = JSONArray.fromObject(SysConf.JSPATCH_CHECK);
		
		for (int i = 0; i < array.size(); i++) {
			JSONObject jspatchInfo = array.getJSONObject(i);
			String ver = jspatchInfo.getString("ver");
			String key = jspatchInfo.getString("key");
			
			if(versionNo.equals(ver)){
				return key;
			}
		}
		return null;
	}
	
	private boolean ver(String lastestVer, String versionNo){
		String [] lastestVerArr = lastestVer.split("\\.");
		String [] verForceArr = versionNo.split("\\.");
		if(Integer.parseInt(lastestVerArr[0]) > Integer.parseInt(verForceArr[0])){
			return true;
		}else{
			if(Integer.parseInt(lastestVerArr[1]) > Integer.parseInt(verForceArr[1])){
				return true;
			}else{
				if(Integer.parseInt(lastestVerArr[1]) < Integer.parseInt(verForceArr[1])){
					return false;
				}
				if(Integer.parseInt(lastestVerArr[2]) > Integer.parseInt(verForceArr[2])){
					return true;
				}else{
					return false;
				}
			}
		}
	}

	@Override
	public Map<String, Object> getConfigConstants(Double levelVer) {
		Map<String, Object> result = new TreeMap<String, Object>();
		
		result.put("env", SysConf.ENV == false ? 0 : 1);
		result.put("baseShareUrl","http://html.kjunsproject.me/content_detail.html?id=");//分享页面地址
		result.put("baseHtmlUrl", "http://html.kjunsproject.me/");//所有html页面前缀
		//result.put("backMasterServer", SysConf.BACK_MASTER_SERVER_URL);//备用主域名
		//result.put("backSearchServer", SysConf.BACK_SEARCH_SERVER_URL);//备用搜索域名
		return result;
	}
	
}
