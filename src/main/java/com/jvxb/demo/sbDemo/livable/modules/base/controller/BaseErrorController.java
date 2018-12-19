package com.jvxb.demo.sbDemo.livable.modules.base.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常控制器：访问页面异常返回特定错误页面。 访问接口异常返回特定信息。
 * 
 * @author 抓娃小兵
 */
@Controller
public class BaseErrorController implements ErrorController {

	/**
	 * 错误页面的路径
	 */
	@Override
	public String getErrorPath() {
		return "frame/error";
	}

	/**
	 * @Description : 处理错误请求，返回错误页面error.html
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/error", produces = "text/html")
	public Object handleErrorHtml(ModelMap modelMap, HttpServletRequest request) {
		modelMap.put("statusCode", request.getAttribute("javax.servlet.error.status_code"));
		modelMap.put("url", request.getAttribute("javax.servlet.error.request_uri"));
		modelMap.put("ex", request.getAttribute("javax.servlet.error.exception"));
		return getErrorPath();
	}

	/**
	 * @Description : 处理错误请求，返回Json数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/error")
	@ResponseBody
	public Object handleError(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("statusCode", request.getAttribute("javax.servlet.error.status_code"));
		resultMap.put("url", request.getAttribute("javax.servlet.error.request_uri"));
		resultMap.put("ex", request.getAttribute("javax.servlet.error.exception"));
		resultMap.put("message", "请求异常！");
		return resultMap;
	}
}
