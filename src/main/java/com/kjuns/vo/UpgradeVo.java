package com.kjuns.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UpgradeVo {
	
	private String isUpdate;
	
	private String latestVer;
	
	private String downLink;
	
	private String discription;
	
	private String changeSplash;
	
	private List<String> imgList = null;
	
	private boolean isNeedJS;
	
	private String jsKey;

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getLatestVer() {
		return latestVer;
	}

	public void setLatestVer(String latestVer) {
		this.latestVer = latestVer;
	}

	public String getDownLink() {
		return downLink;
	}

	public void setDownLink(String downLink) {
		this.downLink = downLink;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getChangeSplash() {
		return changeSplash;
	}

	public void setChangeSplash(String changeSplash) {
		this.changeSplash = changeSplash;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	public boolean getIsNeedJS() {
		return isNeedJS;
	}

	public void setIsNeedJS(boolean isNeedJS) {
		this.isNeedJS = isNeedJS;
	}

	public String getJsKey() {
		return jsKey;
	}

	public void setJsKey(String jsKey) {
		this.jsKey = jsKey;
	}
	
}
