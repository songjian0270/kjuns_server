package com.kjuns.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapUtils {

	private static Logger logger = LoggerFactory.getLogger(MapUtils.class);

	private static final String MAP_ADDRESS_URL = "http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&output=xml&pois=0";

	private static final String MAP_KEY = "CA7f861b3c074cd250c4e167846b4fb7";

	/**
	 * 根据经纬度获取地址信息
	 * 
	 * @param lngLat
	 *            经纬度 格式:经度,纬度
	 * @return
	 * @throws Exception
	 */
	public static String getMapAddress(String lngLat) {
		try {
			StringBuilder sb = new StringBuilder(MAP_ADDRESS_URL);
			sb.append("&ak=");
			sb.append(MAP_KEY);
			sb.append("&location=");
			if (lngLat != null) {
				String[] temp = lngLat.split(",");
				if (temp.length == 2) {
					lngLat = ggToBd(Double.parseDouble(temp[1]), Double.parseDouble(temp[0]));
				}
			}
			sb.append(lngLat);
			String resContet = HttpClientUtil.sendHttpGet(sb.toString());
			String result = getTagTxt("formatted_address", resContet);
			if (result != null) {
				return result;
			}
		} catch (Exception e) {
			logger.error("经纬度：{},查询失败,{}", lngLat, e);
		}

		return "";
	}

	private static final double PI = 3.14159265358979324 * 3000.0 / 180.0;

	/**
	 * 谷歌坐标转换百度坐标
	 * 
	 * @param gg_lon
	 * @param gg_lat
	 * @return
	 */
	private static String ggToBd(double gg_lat, double gg_lon) {
		double x = gg_lon, y = gg_lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
		double bd_lon = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		return bd_lat + "," + bd_lon;
	}

	private static String getTagTxt(String tagName, String src) {
		String startTag = "<" + tagName + ">";
		int startIdx = src.indexOf(startTag);
		String txt = null;
		if (startIdx != -1) {
			int endIdx = src.indexOf("</" + tagName + ">");
			txt = src.substring(startIdx + startTag.length(), endIdx);
		}
		return txt;
	}

	public static void main(String[] args) {
		System.out.println(getMapAddress("121.43842,31.199046"));
	}

}
