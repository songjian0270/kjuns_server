package com.kjuns.interceptor.base; 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjuns.out.BaseOutJB;
import com.kjuns.util.ErrorCode;
import com.kjuns.util.security.SecurityUtils;
import com.thoughtworks.xstream.XStream;

public class BaseInterceptor extends HandlerInterceptorAdapter {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String JSON_SUFFIX = ".json";
	
	private String XML_SUFFIX = ".xml";
	
	protected static final String ERROR = "error";
	
	private static final String ENCODING_UTF8 = "UTF-8";
	
	public static final String ENCRYPT_KEY = "20140808152306151";
	
	protected static ObjectMapper mapper = new ObjectMapper();
	
	protected void sendResponseContent(HttpServletRequest request, HttpServletResponse response, ErrorCode error) {
		BaseOutJB out = new BaseOutJB(error);
		writeResponse(request, response, out);
	}
	
	protected void sendResponseContent(HttpServletRequest request, HttpServletResponse response, ErrorCode error, Object data){
		BaseOutJB out = new BaseOutJB(error, data);
		writeResponse(request, response, out);
	}
	
	protected void writeResponse(HttpServletRequest request, HttpServletResponse response, BaseOutJB out){
		try {
//			response.setContentType("text/json; charset=utf-8");
			response.setCharacterEncoding(ENCODING_UTF8);
			PrintWriter p = response.getWriter();
			XStream xstream = new XStream();  
			xstream.autodetectAnnotations(true); 
			if(request.getRequestURI().indexOf(JSON_SUFFIX) > 0){
				response.setContentType("application/json"); 
				String json = mapper.writeValueAsString(out);
				p.write(json);
			}else if(request.getRequestURI().indexOf(XML_SUFFIX) > 0){
				//oxstream.registerConverter(new NullConverter());
				response.setContentType("application/xml");
				p.write(xstream.toXML(out));
			}else{
				response.setContentType("application/json"); 
				String json = mapper.writeValueAsString(out);
				p.write(json);
			}
		}catch (Exception e) {
			logger.error("",e);
		} 
	}
	
	/**
	 * giz 压缩
	 * @param request
	 * @param encrypted
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map<String,Object> handleRequest(HttpServletRequest request, boolean encrypted){
		long start = System.currentTimeMillis();  
		Map<String,Object> dataMap = null;
		ServletInputStream in = null;
		BufferedReader reader = null;
		GZIPInputStream gzipIn = null;
		try {
			in = request.getInputStream();
			gzipIn = new GZIPInputStream(in);
			reader = new BufferedReader(new InputStreamReader(gzipIn,ENCODING_UTF8));
			String jsonStr = reader.readLine();
			String nextLine = null;
			if((nextLine = reader.readLine()) != null){//判断是否多行
				StringBuilder sb = new StringBuilder(jsonStr);
				sb.append(nextLine);
				while ((nextLine = reader.readLine()) != null) {
					sb.append(nextLine);
				}
				jsonStr = sb.toString();
			}
			if(!StringUtils.hasText(jsonStr)){
				dataMap = new HashMap<>();
				dataMap.put(ERROR, ErrorCode.INPUT_ERROR);
				return dataMap;
			}
			logger.info("<<<<<<<<Received jsonStr:{}",jsonStr);
			logger.info("接收报文用时：{}ms",System.currentTimeMillis()-start);
			if(encrypted == true){
				//由于负载均衡暂时不能共享session，所以加密key暂时写固定
				/*HttpSession hs = request.getSession();
				String key = (String) hs.getAttribute(BaseController.ENCRYP_KEY);
				logger.info("SessionId:'{}' get key:'{}'",hs.getId(),key);
				jsonStr = SecurityUtils.decryption(key, jsonStr);
				logger.info("<<<<<<<<（key:{}）解密后:{}", key,jsonStr);*/
				jsonStr = SecurityUtils.decryption(ENCRYPT_KEY, jsonStr);
				logger.info("<<<<<<<<解密后:{}", jsonStr);
			}
			long start1 = System.currentTimeMillis();  
			dataMap = mapper.readValue(jsonStr, Map.class);//json转成map
			logger.info("解析JSON报文用时：{}ms",System.currentTimeMillis()-start1); 
		} catch (SocketTimeoutException e) {
			dataMap = new HashMap<>();
			dataMap.put(ERROR, ErrorCode.NET_ERROR_READ_TIMED_OUT);
			logger.error("接收/读取请求报文超时：", e);
		} catch (Exception e) {
			dataMap = new HashMap<>();
			dataMap.put(ERROR, ErrorCode.INPUT_ERROR);
			logger.error("接收/读取请求报文出错：", e);
		} finally{
			if(reader != null){
				try {
					reader.close();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			if(gzipIn != null){
				try {
					gzipIn.close();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			if(in != null){
				try {
					in.close();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		}
		return dataMap;
	}

	
}
 