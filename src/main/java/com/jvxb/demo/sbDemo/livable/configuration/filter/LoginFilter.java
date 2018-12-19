package com.jvxb.demo.sbDemo.livable.configuration.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 登录过滤器：未登录访问时需转至登录界面
 * 
 * @author 抓娃小兵
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter extends OncePerRequestFilter {

	private final Set<String> notFilterSet = new HashSet<>();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 获取请求的uri
		String uri = request.getRequestURI();

		// 资源文件不过滤
		if (uri.endsWith(".js") || uri.endsWith(".css") || uri.endsWith(".woff") || uri.endsWith(".png")
				|| uri.endsWith(".jpg")) {
			filterChain.doFilter(request, response);
			return;
		}

		if (notFilterSet.isEmpty()) {
			// 其它不过滤的uri
			notFilterSet.add(request.getContextPath() + "/admin/login");
			notFilterSet.add(request.getContextPath() + "/admin/checkLogin");
		}

		if (notFilterSet.contains(uri)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 某些特定接口不过滤: 系统Api提供数据的接口
		if (uri.startsWith("/admin/demo/api")) {
			filterChain.doFilter(request, response);
			return;
		}

		// 执行过滤:从session中获取登录者实体
		Object obj = request.getSession().getAttribute("user");
		if (null == obj) {
			response.sendRedirect(request.getContextPath() + "/admin/login");
		} else {
			filterChain.doFilter(request, response);
		}

	}

}
