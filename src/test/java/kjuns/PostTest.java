package kjuns;

import java.util.Map;
import java.util.TreeMap;

import com.kjuns.util.CommonUtils;

public class PostTest {

	public static String url = "http://localhost:8080/kjuns_server/";

	public static void main(String[] args) throws Exception {

		TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
		
		//登录
//		treeMap.put("cellPhoneNumber", "86-18321971617");
//		treeMap.put("checkCode", "8888");
		
		//完善信息
		treeMap.put("token","43c50368580447b19cd53c3d418f5851");
	/*		treeMap.put("nickName","zxczxczxczxczxczx");*/
//		treeMap.put("faceSrc","zxzxczxczxczxc");
//		treeMap.put("sex", 0);
//		treeMap.put("idcard","512402525215252425");
		/*treeMap.put(key, value)*/

		StringBuffer sbff = new StringBuffer();
		StringBuffer sbffkey = new StringBuffer();
		// 字典排序
		for (Map.Entry<String, Object> m : treeMap.entrySet()) {
			sbff.append(m.getKey()).append(m.getValue());
			sbffkey.append(m.getKey()).append("=").append(m.getValue()).append("&");
		}
		String keyStr = sbff.toString().toUpperCase()
				+ "QcZs3U5bpMdPCszaw3IbR8aA88FZsuLdLuVB2max9/mEO+YW7hNoHpW3nelGSyQpWzZSMwfbLEoVRVWEuqoPng==";

		String sign = CommonUtils.md5Encode32(keyStr);

	//	String reStr = HttpRequest.sendPost("http://localhost:8080/kjuns_server/user/login", sbffkey+"signature=" + sign);
	//	String reStr = HttpRequest.sendPost("http://localhost:8080/kjuns_server/user/complete", sbffkey+"signature=" + sign);
	//	String reStr = HttpRequest.sendPost("http://localhost:8080/kjuns_server/user/info/detail", sbffkey+"signature=" + sign);
		
		String reStr = HttpRequest.sendPost("http://localhost:8080/kjuns_server/user/info/detail", sbffkey+"signature=" + sign);
		
		System.out.println(reStr);


	

	}

}
