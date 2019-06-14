package com.jvxb.demo.sbDemo.livable.configuration.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jvxb.demo.sbDemo.livable.configuration.interceptor.ApiRequestInterceptor;

/** 
 * 登录过滤器：未登录访问时需转至登录界面
 * 
 * @author 抓娃小兵
 */
@Order(1)
@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter extends OncePerRequestFilter {

	/**
	 * 授权的请求List 1.登录和验证登录的接口不过滤 2.系统Api提供数据的接口不过滤，将由{@link ApiRequestInterceptor}管理
	 */
	private final String[] licensedRequestArr = {"/admin/login", "/admin/checkLogin", "/admin/api"};

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 获取请求的uri
		String uri = request.getRequestURI();
		// 获取容器路径
		String ctxPath = request.getContextPath();

		// 资源文件不过滤: 以.js|css|png|jpg|jpeg|gif|ico|woff结尾的
		if (uri.matches(".+\\.(html|js|css|png|jpg|jpeg|gif|ico|ttf|woff|woff2)$")) {
			filterChain.doFilter(request, response);
			return;
		}

		// 授权的请求不过滤
		if (isLicensedRequest(licensedRequestArr, ctxPath, uri)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 否则执行过滤:未登录者须先前往登录界面登录
		Object obj = request.getSession().getAttribute("user");
		if (null == obj) {
			response.sendRedirect(ctxPath + "/admin/login");
		} else {
			filterChain.doFilter(request, response);
		}

	}

	/**
	 * 授权不过滤的uri返回true
	 */
	private boolean isLicensedRequest(String[] licensedRequestArr, String ctxPath, String uri) {
		for (String licensedRequest : licensedRequestArr) {
			if (uri.startsWith(ctxPath + licensedRequest)) {
				return true;
			}
		}
		return false;
	}

}
