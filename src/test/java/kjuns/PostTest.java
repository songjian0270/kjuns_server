package kjuns;

import java.util.Map;
import java.util.TreeMap;

import com.kjuns.util.CommonUtils;

public class PostTest {

	public static String url = "http://localhost:8080/kjuns_server/";

	public static void main(String[] args) throws Exception {

		TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
		
		//登录
		treeMap.put("cellPhoneNumber", "86-18321971617");
		treeMap.put("checkCode", "8888");
		
		//完善信息
//		treeMap.put("id","383248073cff4e089604838f7c97ed94");
//		treeMap.put("nickName","zxczxczxczxczxczx");
//		treeMap.put("faceSrc","zxzxczxczxczxc");
//		treeMap.put("sex", 0);
//		treeMap.put("idcard","512402525215252425");

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

		String reStr = HttpRequest.sendPost("http://localhost:8080/kjuns_server/user/login", sbffkey+"signature=" + sign);
	//	String reStr = HttpRequest.sendPost("http://localhost:8080/kjuns_server/user/complete", sbffkey+"signature=" + sign);
		System.out.println(reStr);


	

	}

}
