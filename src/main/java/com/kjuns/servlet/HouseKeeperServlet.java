package com.kjuns.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.logicalcobwebs.proxool.ProxoolFacade;
/**
 * <b>Function: </b> 解决class类进行修改后保存，服务自动重新启动报错的问题    [报错信息：Exception in thread "HouseKeeper" java.lang.NullPointerException]
 * @author James
 * @date 2015-04-12
 * @file HouseKeeperServlet.java
 * @package com.kjuns.servlet
 * @project kjuns
 * @version 1.0
 */
public class HouseKeeperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void destroy() {
		// 此处添加处理
		ProxoolFacade.shutdown();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
