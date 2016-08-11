package com.kjuns.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.kjuns.controller.BaseController;

@Aspect
@Component
public class RepeatSubmitAspect extends BaseController {

//	private static final Logger logger = LoggerFactory.getLogger("RepeatSubmitAspect");
//
//	@Before("within(@org.springframework.stereotype.Controller *) && @annotation(repeatSubmit)")
//	public void verifyToken(final JoinPoint joinPoint, RepeatSubmit repeatSubmit) throws Exception {
//		Object[] args = joinPoint.getArgs();
//		HttpServletRequest request = null;
//		for (int i = 0; i < args.length; i++) {
//			if (args[i] instanceof HttpServletRequest) {
//				request = (HttpServletRequest) args[i];
//				break;
//			}
//		}
//		if (request == null) {
//			throw new Exception("方法中缺失HttpServletRequest参数");
//		}
//		
//		LinkedHashMap<String, Object> data = this.handleRequestDate(request);
//		String ip = CommonUtils.getIpAddr(request);
//		String url = request.getRequestURL().toString();
//		String key = "req_submit_".concat(url).concat(ip).concat(data.toString());
//		long count = JedisPoolCacheUtils.incrBy(key, 1L);
//		JedisPoolCacheUtils.expire(key, 30);
//		if (count > 1) {
//			logger.error("不能重复提交");
//			throw new RepeatSubmitException("数据不能重复提交");
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	private LinkedHashMap<String,Object> handleRequestDate(HttpServletRequest request) throws UnsupportedEncodingException{
//		LinkedHashMap<String,Object> data = new LinkedHashMap<>();//有序的map，顺序同validation的xml文件中配置的字段顺序，即字段校验顺序
//		Enumeration<String> pNames = request.getParameterNames();
//		while(pNames.hasMoreElements()){
//			String fieldName = pNames.nextElement();
//			String fieldVal = request.getParameter(fieldName);
//			data.put(fieldName, fieldVal);
//		}
//		return data;
//	}
}
