package com.kjuns.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.kjuns.controller.BaseController;

@Aspect
@Component
public class RequestLimitAspect extends BaseController {

//	private static final Logger logger = LoggerFactory.getLogger("RequestLimitAspect");
//
//	@Before("within(@org.springframework.stereotype.Controller *) && @annotation(limit)")
//	public void requestLimit(final JoinPoint joinPoint, RequestLimit limit) throws Exception {
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
//		String ip = CommonUtils.getIpAddr(request);
//		String url = request.getRequestURL().toString();
//		String key = "req_limit_".concat(url).concat(ip);
//		long count = JedisPoolCacheUtils.incrBy(key, 1L);
//		if (count == 1) {
//			JedisPoolCacheUtils.expire(key, limit.time());
//		}
//		if (count > limit.count()) {
//			logger.error("用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + limit.count() + "]");
//			throw new Exception("HTTP请求超出设定的限制");
//		}
//	}

}
